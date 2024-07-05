package com.dev.server.util;

public class Constants {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_USER = "USER";

    public static final String[] AUTH_WHITELIST = {
        "/v1/api-docs",
        "/api/v1/auth/login", 
        "/api/v1/auth/register",
        "/api/v1/auth/refresh",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
    };
}
