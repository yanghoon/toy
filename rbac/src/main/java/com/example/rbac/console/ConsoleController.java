package com.example.rbac.console;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.example.rbac.console.CompanyService.Company;
import com.example.rbac.console.CompanyService.CompanyConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;

@RestController
public class ConsoleController {
    @Autowired
    private CompanyService companies;

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
    public ServerResponse createCompanies(@RequestParam("realm") String realm) throws URISyntaxException {
        companies.create(realm);
        URI uri = new URI("/companies");
		return ServerResponse.created(uri).build();
    }

    @PutMapping("/companies/{realm}")
    public ServerResponse enabled(@PathVariable String realm, @RequestParam boolean enabled) {
        companies.enabled(realm, enabled);
		return ServerResponse.ok().build();
    }

    @DeleteMapping("/companies/{realm}")
    public ServerResponse remove(@PathVariable String realm) {
        companies.remove(realm);
		return ServerResponse.ok().build();
    }

    @GetMapping("/companies/{realm}/config")
    public CompanyConfig config(@PathVariable String realm) {
        return companies.config(realm);
    }
}