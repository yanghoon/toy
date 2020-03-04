package com.example.rbac.grafana;

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
public class User {
    private Integer id;
    private String name;
    private String email;
    private String login;
    // for registration
    private String password;
    // for response
    private String massage;
    // for lookup
    private String theme;
    private Integer orgId;
    private Boolean isGrafanaAdmin;
    private Boolean isDisabled;
    private Boolean isExternal;
    private String authLabels;
    // private Date updatedAt;
    // private Date createdAt;
}
