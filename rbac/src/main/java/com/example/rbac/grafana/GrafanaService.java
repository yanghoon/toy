package com.example.rbac.grafana;

import com.example.rbac.grafana.Org.OrgCreate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @see https://square.github.io/retrofit/
 */
public interface GrafanaService {
    // https://grafana.com/docs/grafana/latest/http_api/auth/#create-api-token
    // https://grafana.com/docs/grafana/latest/http_api/org/#create-organization

    // for Org
    @GET("/api/orgs/name/{orgName}")
    Call<Org> getOrg(@Path("orgName") String orgName);

    @POST("/api/orgs")
    Call<OrgCreate> create(@Body OrgCreate org);

    @DELETE("/api/orgs/{orgId}")
    Call<GrafanaRes> removeOrg(@Path("orgId") Integer orgId);

    // for Users in Org
    @POST("/api/orgs/{orgId}/users")
    Call<OrgUser> invite(@Path("orgId") Integer orgId, @Body OrgUser user);

    @DELETE("/api/orgs/{orgId}/users/{userId}")
    Call<GrafanaRes> leave(@Path("orgId") Integer orgId, @Path("userId") Integer userId);

    // for User
    @GET("/api/users/lookup?")
    Call<User> lookup(@Query("loginOrEmail") String email);

    // for Admin API
    @POST("/api/admin/users")
    Call<User> create(@Body User org);
}