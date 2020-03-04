package com.example.rbac.grafana;

import com.example.rbac.grafana.Org.Role;

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
public class OrgUser {
    @Builder.Default
    private Role role = Role.Viewer;
    private String loginOrEmail;
    // for response
    private String massage;
}
