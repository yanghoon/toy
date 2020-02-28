package com.example.rbac.console;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.example.rbac.console.CompanyService.Company;
import com.example.rbac.console.CompanyService.CompanyConfig;
import com.example.rbac.console.ProjectService.Project;
import com.example.rbac.console.UserService.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.function.ServerResponse;

@RestController
public class ConsoleController {
    @Autowired
    private CompanyService companies;

    @Autowired
    private ProjectService projects;

    @Autowired
    private UserService users;

    @GetMapping("/companies")
    public List<Company> companies() {
        return companies.list();
    }

    /**
     * @see https://kimseunghyun76.tistory.com/448
     * @param realmName
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/companies/new")
    public ResponseEntity<?> createCompanies(@RequestParam("realm") String realm) throws URISyntaxException {
        companies.create(realm);
        URI uri = new URI("/companies");
		return ResponseEntity.created(uri).build();
    }

    @PutMapping("/companies/{realm}")
    public ResponseEntity<?> enabled(@PathVariable String realm, @RequestParam boolean enabled) {
        companies.enabled(realm, enabled);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/companies/{realm}")
    public ResponseEntity<?> remove(@PathVariable String realm) {
        companies.remove(realm);
		return ResponseEntity.ok().build();
    }

    @GetMapping("/companies/{realm}/config")
    public CompanyConfig config(@PathVariable String realm) {
        return companies.config(realm);
    }

    /**
     *  for Projects
     */
    @GetMapping("/companies/{realm}/projects")
    public List<Project> projects(@PathVariable String realm) {
        return projects.list(realm);
    }

    @PostMapping("/companies/{realm}/projects/new")
    public ResponseEntity<?> create(@PathVariable String realm, @RequestParam String project) throws Exception {
        projects.create(realm, project);
        URI uri = new URI("/companies/" + realm + "/projects/" + project);
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/companies/{realm}/projects/{project}")
    public ResponseEntity<?> remove(@PathVariable String realm, @PathVariable String project) throws Exception {
        projects.remove(realm, project);
        return ResponseEntity.ok().build();
    }

    /**
     *  for User
     */
    @GetMapping("/companies/{realm}/projects/{project}/members")
    public List<User> members(@PathVariable String realm, @PathVariable String project) {
        return users.list(realm, project);
    }

    @GetMapping("/companies/{realm}/users")
    public List<User> searchUser(@PathVariable String realm, @RequestParam String keyword) {
        return users.search(realm, keyword);
    }

    @PutMapping("/companies/{realm}/projects/{project}/members/{username}")
    public ResponseEntity<?> invite(@PathVariable String realm, @PathVariable String project, @PathVariable String username) {
        users.invite(realm, project, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/companies/{realm}/projects/{project}/members/{username}")
    public ResponseEntity<?> leave(@PathVariable String realm, @PathVariable String project, @PathVariable String username) {
        users.leave(realm, project, username);
        return ResponseEntity.ok().build();
    }

    // @PostMapping("/companies/{realm}/projects/new")
    // public ResponseEntity<?> create(@PathVariable String realm, @RequestParam String project) throws Exception {
    //     projects.create(realm, project);
    //     URI uri = new URI("/companies/" + realm + "/projects/" + project);
    //     return ResponseEntity.created(uri).build();
    // }

    // @DeleteMapping("/companies/{realm}/projects/{project}")
    // public ResponseEntity<?> remove(@PathVariable String realm, @PathVariable String project) throws Exception {
    //     projects.remove(realm, project);
    //     return ResponseEntity.ok().build();
    // }
}