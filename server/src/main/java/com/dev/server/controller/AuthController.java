package com.dev.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.server.dto.APIResponse;
import com.dev.server.dto.AdminDTO.AdminResponse;
import com.dev.server.dto.AuthDTO.AuthLoginRequest;
import com.dev.server.dto.AuthDTO.AuthRegisterRequest;
import com.dev.server.dto.AuthDTO.AuthResponse;
import com.dev.server.exception.ErrorCode;
import com.dev.server.service.AuthService;
import com.dev.server.service.JwtService;
import com.dev.server.util.MessageConstant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Register new admin",
        description = "Endpoint for registering new administrator. \n\n"
                + "Parameters: None\n"
                + "Request body:\n"
                + "- fullName (string): Admin full name\n"
                + "- username (string): Admin username\n"
                + "- email (string): Admin email\n"
                + "- password (string): Admin password\n\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (object): Admin information\n"
                + "  - id (number): Admin ID\n"
                + "  - fullName (string): Admin full name\n"
                + "  - username (string): Admin username\n"
                + "  - email (string): Admin email"
                + "  - role (string): Admin role\n"
                + "  - roleMenu (string): String of menu indexes\n"

    )
    @PostMapping(path = "/auth/register")
    public ResponseEntity<APIResponse<AdminResponse>> register(@Valid @RequestBody AuthRegisterRequest request) {
        AdminResponse newAdmin = this.authService.register(request);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.USER_REGISTERED, newAdmin), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Admin login",
        description = "Endpoint for admin login. \n\n"
                + "Parameters: None\n"
                + "Request body:\n"
                + "- username (string): Admin username\n"
                + "- password (string): Admin password\n\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (object): Authentication information\n"
                + "  - access_token (string): Access token\n"
                + "  - refresh_token (string): Refresh token\n"
                + "  - token_type (string): Token type\n"
                + "  - expires_in (number): Token expiration time (seconds)"
    )
    @PostMapping(path = "/auth/login")
    public ResponseEntity<APIResponse<AuthResponse>> login(@Valid @RequestBody AuthLoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = this.authService.login(request);
        Cookie cookie = new Cookie("refreshToken", authResponse.getRefresh_token());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(this.authService.getRefreshTokenExpiration());
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.AUTHORIZED, authResponse), HttpStatus.OK);
    }

    @Operation(
        summary = "Refresh admin login token",
        description = "Endpoint for refreshing login token of admin. \n\n"
                + "Parameters: None\n"
                + "Request body: None\n"
                + "Response body:\n"
                + "- success (boolean): Response status\n"
                + "- message (string): Response message\n"
                + "- data (object): Authentication information\n"
                + "  - access_token (string): Access token\n"
                + "  - refresh_token (string): Refresh token\n"
                + "  - token_type (string): Token type\n"
                + "  - expires_in (number): Token expiration time (seconds)"
    )
    @PostMapping(path = "/auth/refresh")
    public ResponseEntity<APIResponse<AuthResponse>> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            return new ResponseEntity<>(new APIResponse<>(false, MessageConstant.INVALID_REFRESH_TOKEN, ErrorCode.INVALID_REFRESH_TOKEN.getCode()), HttpStatus.UNAUTHORIZED);
        }
        AuthResponse authResponse = this.authService.refresh(refreshToken);
        return new ResponseEntity<>(new APIResponse<>(true, MessageConstant.TOKEN_REFRESHED, authResponse), HttpStatus.OK);
    }
}
