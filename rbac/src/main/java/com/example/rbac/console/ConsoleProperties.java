package com.example.rbac.console;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.rbac.console.CompanyService.Tool;

@Component
@ConfigurationProperties("")
public class ConsoleProperties {
    private Map<String, Tool> console;

    public void set(String name, Tool tool){
        console.put(name, tool);
    }
    public Tool get(String name){
        return console.get(name);
    }
    public void setConsole(Map<String, Tool> console) {
        this.console = console;
    }
    public Map<String, Tool> getConsole(){
        return console;
    }
}