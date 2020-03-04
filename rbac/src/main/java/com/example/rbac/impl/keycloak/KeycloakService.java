package com.example.rbac.impl.keycloak;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.rbac.console.CompanyService;
import com.example.rbac.console.ProjectService;
import com.example.rbac.console.UserService;
import com.example.rbac.grafana.GrafanaRes;
import com.example.rbac.grafana.GrafanaService;
import com.example.rbac.grafana.Org;
import com.example.rbac.grafana.Org.OrgCreate;
import com.example.rbac.grafana.OrgUser;
import com.example.rbac.grafana.User.UserBuilder;
import com.example.rbac.impl.keycloak.KeycloakConfig.IntegrationProperties;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import retrofit2.Response;

/**
 * @see https://www.keycloak.org/docs-api/9.0/rest-api/
 * @see https://github.com/keycloak/keycloak/releases
 */
public class KeycloakService implements CompanyService, ProjectService, UserService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Keycloak keycloak;

    @Autowired
    private IntegrationProperties integrations;

    private Company toCompany(RealmRepresentation r) {
        String name = r.getRealm();
        boolean enabled = r.isEnabled();
        Company c = new Company(name, enabled);
        return c;
    }

    @Override
    public List<Company> list() {
        return keycloak.realms().findAll().stream().map(r -> toCompany(r)).collect(Collectors.toList());
    }

    /**
     * @see https://github.com/keycloak/keycloak/blob/master/testsuite/integration-arquillian/tests/base/src/test/java/org/keycloak/testsuite/admin/realm/RealmTest.java#L145
     */
    public void create(String name) {
        // TEST:
        // curl -X POST -d name=_test http://localhost:8090/companies
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(name);
        keycloak.realms().create(realm);

        /**
         * ERR: 400 Bad Request - Unrecognized field
         * "webAuthnPolicyPasswordlessRpEntityName" (class
         * org.keycloak.representations.idm.RealmRepresentation), not marked as
         * ignorable - keycloak-admin-client (9.0.0 -> 8.0.0) ERR: 409 Conflict -
         * {"errorMessage":"Conflict detected. See logs for details"}
         */
    }

    public void enabled(String name, boolean enabled) {
        // TEST:
        // curl -X PUT -d enabled=true http://localhost:8090/companies/_test
        // curl -X PUT -d enabled=false http://localhost:8090/companies/_test
        RealmResource res = keycloak.realm(name);
        RealmRepresentation rep = res.toRepresentation();
        rep.setEnabled(enabled);
        res.update(rep);
    }

    public void remove(String name) {
        // TEST:
        // curl -X DELETE http://localhost:8090/companies/_test
        RealmResource res = keycloak.realm(name);
        res.remove();
    }

    public Integrations integrations(String name) {
        return integrations.getIntegrations(name);
    }

    /**
     * for Project
     */
    private Project toProject(GroupRepresentation g) {
        String id = g.getId();
        String name = g.getName();
        String path = g.getPath();
        List<Project> subgroups = g.getSubGroups().stream().map(sg -> toProject(sg)).collect(Collectors.toList());
        Project p = new Project(id, name, path, subgroups);
        return p;
    }

    public List<Project> list(String company) {
        // TEST:
        // curl -X GET http://localhost:8090/companies/dummy/projects
        return keycloak.realm(company).groups().groups().stream().map(r -> toProject(r)).collect(Collectors.toList());
    }

    public void create(String company, String project) throws IOException {
        // TEST:
        // curl -X POST -d project=proj-a
        // http://localhost:8090/companies/dummy/projects/new
        GroupRepresentation group = new GroupRepresentation();
        group.setName(project);
        keycloak.realm(company).groups().add(group);

        // Org org = Org.builder().name(project).build();
        OrgCreate org = OrgCreate.builder().name(project).build();
        Response<OrgCreate> res = grafana.create(org).execute();
        log.info("{}", res.toString());
        log.debug("Response.body - {}", res.body());
        log.debug("Response.errorBody - {}", res.errorBody());
    }

    public void remove(String company, String project) throws IOException {
        // TEST:
        // curl -X DELETE http://localhost:8090/companies/dummy/projects/proj-a
        try {
            GroupResource group = keycloak.realm(company).groups().group(project);
            GroupRepresentation info = group.toRepresentation();
            String name = info.getName();
            group.remove();

            Response<Org> orgRes = grafana.getOrg(name).execute();
            if(orgRes.code() == 200){
                int orgId = orgRes.body().getId();
                grafana.removeOrg(orgId).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private GrafanaService grafana;

    /**
     * for User & RBAC
     */

    private User toUser(UserRepresentation u) {
        String id = u.getId();
        String name = u.getUsername();
        String email = u.getEmail();
        return User.builder().id(id).name(name).email(email).build();
    }

    public List<User> list(String company, String project) {
        GroupResource group = keycloak.realm(company).groups().group(project);
        List<UserRepresentation> members = group.members();
        return members.stream().map(u -> toUser(u)).collect(Collectors.toList());
    }

    public List<User> search(String company, String keyword) {
        List<UserRepresentation> users = keycloak.realm(company).users().search(keyword, 0, 10);
        return users.stream().map(u -> toUser(u)).collect(Collectors.toList());
    }

    public void invite(String company, String groupId, String userId) {
        try {
            UserResource user = keycloak.realm(company).users().get(userId);
            user.joinGroup(groupId);

            UserRepresentation info = user.toRepresentation();
            String email = info.getEmail();

            // create grafana user
            com.example.rbac.grafana.User guser = null;
            Response<com.example.rbac.grafana.User> res = grafana.lookup(email).execute();
            if(res.code() != 200){
                UserBuilder builder = com.example.rbac.grafana.User.builder();
                builder.name(info.getUsername())
                    .email(info.getEmail())
                    .login(info.getEmail())
                    .password("user");

                res = grafana.create(builder.build()).execute();
                guser = res.body();
                log.info("{}", res.toString());
                log.debug("Response.body - {}", res.body());
                log.debug("Response.errorBody - {}", res.errorBody());
            } else {
                guser = res.body();
            }

            // join grafana user
            GroupRepresentation group = keycloak.realm(company).groups().group(groupId).toRepresentation();
            Response<Org> orgRes = grafana.getOrg(group.getName()).execute();
            int orgId = orgRes.body().getId();
            OrgUser orgUser = OrgUser.builder().loginOrEmail(email).build();
            Response<OrgUser> resOrgUser = grafana.invite(orgId, orgUser).execute();
            log.info("{}", resOrgUser.toString());
            log.debug("Response.body - {}", resOrgUser.body());
            log.debug("Response.errorBody - {}", resOrgUser.errorBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void leave(String company, String groupId, String userId) {
        try{
            UserResource user = keycloak.realm(company).users().get(userId);
            UserRepresentation userInfo = user.toRepresentation();

            GroupResource group = keycloak.realm(company).groups().group(groupId);
            GroupRepresentation groupInfo = group.toRepresentation();

            String groupName = groupInfo.getName();
            String email = userInfo.getEmail();

            user.leaveGroup(groupId);
            Response<Org> orgRes = grafana.getOrg(groupName).execute();
            Response<com.example.rbac.grafana.User> orgUserRes = grafana.lookup(email).execute();
            if(orgRes.code() == 200 && orgUserRes.code() == 200){
                int orgId = orgRes.body().getId();
                int orgUserId = orgUserRes.body().getId();
                Response<GrafanaRes> res = grafana.leave(orgId, orgUserId).execute();
                log.info("{}", res.toString());
                log.debug("Response.body - {}", res.body());
                log.debug("Response.errorBody - {}", res.errorBody());
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}