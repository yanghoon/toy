package com.example.rbac.grafana;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @see https://square.github.io/retrofit/
 */
public interface GrafanaService {
    // https://grafana.com/docs/grafana/latest/http_api/auth/#create-api-token
    // https://grafana.com/docs/grafana/latest/http_api/org/#create-organization

    @POST("/api/orgs")
    Call<Org> create(@Body Org org);
}