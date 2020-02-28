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
public class Org {
    private Integer orgId;
    private String name;
    private String message;
}
