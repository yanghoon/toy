package com.example.rbac.grafana;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see https://stackoverflow.com/a/59275719
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Org {
    private Integer id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Role {
        Viewer, Editor, Admin;
        // VIEWER("Viewer"), EDITOR("Editor"), ADMIN("Admin");
        // private String val;
        // Role(String val){ this.val = val; }
        // public String toString(){ return val; }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrgCreate {
        private Integer orgId;
        private String name;
        private String message;  // for response
    }

    @Data
    @NoArgsConstructor
    public static class OrgDelete {
        private String message;  // for response
    }
}
