package com.dev.server.util;

public class Constants {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_USER = "USER";

    public static final String[] AUTH_WHITELIST = {
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/api/v1/auth/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/swagger-ui/**",
    };
}
