package com.example.rbac.impl.keycloak;

import java.util.List;
import java.util.stream.Collectors;

import com.example.rbac.console.CompanyService;
import com.example.rbac.console.ProjectService;
import com.example.rbac.impl.keycloak.KeycloakConfig.CompanyProperties;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @see https://www.keycloak.org/docs-api/9.0/rest-api/
 * @see https://github.com/keycloak/keycloak/releases
 */
public class KeycloakService implements CompanyService, ProjectService {
    @Autowired
    private Keycloak keycloak;

    @Autowired
    private CompanyProperties config;
    
    private Company toCompany(RealmRepresentation r) {
        String name = r.getRealm();
        boolean enabled = r.isEnabled();
        Company c = new Company(name, enabled);
        return c;
    }

    public List<Company> list() {
        return keycloak.realms()
                    .findAll()
                    .stream()
                    .map(r -> toCompany(r))
                    .collect(Collectors.toList());
    }

    /**
     * @see https://github.com/keycloak/keycloak/blob/master/testsuite/integration-arquillian/tests/base/src/test/java/org/keycloak/testsuite/admin/realm/RealmTest.java#L145
     */
	public void create(String name) {
        // TEST:
        // curl -X POST -d name=_test  http://localhost:8090/companies
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(name);
        keycloak.realms().create(realm);
        
        /**
         * ERR: 400 Bad Request
         *  - Unrecognized field "webAuthnPolicyPasswordlessRpEntityName" (class org.keycloak.representations.idm.RealmRepresentation), not marked as ignorable
         *  - keycloak-admin-client (9.0.0 -> 8.0.0)
         * ERR: 409 Conflict
         *  - {"errorMessage":"Conflict detected. See logs for details"}
         */
    }

    public void enabled(String name, boolean enabled) {
        // TEST:
        // curl -X PUT -d enabled=true  http://localhost:8090/companies/_test
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

    public CompanyConfig config(String name) {
        return config.getConfig(name);
    }
}