package com.example.rbac.retrofit;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Request.Builder;

/**
 * @see https://futurestud.io/tutorials/android-basic-authentication-with-retrofit
 */
public class BasicAuthInterceptor implements Interceptor {
    private String token;
    // private String username;
	// private String password;

    public BasicAuthInterceptor(String username, String password) {
        // this.username = username;
        // this.password = password;
        this.token = Credentials.basic(username, password);
    }

    public Response intercept(Chain chain) throws IOException {
        Builder builder = chain.request().newBuilder();
        Request newRequest = builder.addHeader("Authorization", token).build();
        return chain.proceed(newRequest);
    }
}