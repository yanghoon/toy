package test.model;

import java.net.URL;
import java.util.Map;

import lombok.Data;

@Data
public class Tool {
    private String kind;
    private String name;
    private URL endpoint;
    private Map<String, String> attributes;
}