package com.example.rbac.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;

public class BearerInterceptor implements Interceptor {
    private String token;

    public BearerInterceptor(String token) {
        this.token = token;
    }

    public Response intercept(Chain chain) throws IOException {
        Builder builder = chain.request().newBuilder();
        Request newRequest = builder.addHeader("Authorization", "Bearer " + token).build();
        return chain.proceed(newRequest);
    }
}