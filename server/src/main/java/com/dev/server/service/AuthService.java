package com.dev.server.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.server.dto.AdminDTO.AdminResponse;
import com.dev.server.dto.AuthDTO.AuthLoginRequest;
import com.dev.server.dto.AuthDTO.AuthRegisterRequest;
import com.dev.server.dto.AuthDTO.AuthResponse;
import com.dev.server.exception.AppException;
import com.dev.server.exception.ErrorCode;
import com.dev.server.model.Admin;
import com.dev.server.repository.AdminRepository;
import com.dev.server.util.Helper;

@Service
public class AuthService {
    @Value("${security.refresh.length}")
    private int refreshTokenLength;

    @Value("${security.refresh.expiration}")
    private int refreshTokenExpiration;

    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RedisService redisService;

    public AuthService(@Lazy AuthenticationManager authenticationManager, AdminRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    public AdminResponse register(AuthRegisterRequest request) {
        Optional<Admin> admin = repository.findByUsername(request.getUsername());
        if (admin.isPresent()) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        admin = repository.findByEmail(request.getEmail());
        if (admin.isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        Admin newAdmin = new Admin(request.getFullName(), request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        return convertToDTO(repository.save(newAdmin));
    }

    public AuthResponse login(AuthLoginRequest request) {
        Optional<Admin> admin = repository.findByUsername(request.getUsername());
        if (admin.isEmpty() || !passwordEncoder.matches(request.getPassword(), admin.get().getPassword())) {
            throw new AppException(ErrorCode.INVALID_LOGIN);
        }
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String refreshToken = this.getRefreshToken();
        admin.get().setRefreshToken(refreshToken);
        admin.get().setRefreshTokenExpiration(LocalDateTime.now().plusSeconds(this.refreshTokenExpiration));
        repository.save(admin.get());
        String token = jwtService.generateToken(admin.get());
        AuthResponse authResponse = new AuthResponse(token, jwtService.getExpirationTime(), refreshToken);
        return authResponse;
    }

    public void logout(String refreshToken) {
        Admin admin = repository.findByRefreshToken(refreshToken).orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));
        admin.setRefreshToken(null);
        admin.setRefreshTokenExpiration(null);
        this.redisService.addToBlacklist(refreshToken);
        repository.save(admin);
    }

    public String getRefreshToken() {
        return Helper.generateRandomString(this.refreshTokenLength);
    }

    public int getRefreshTokenExpiration() {
        return this.refreshTokenExpiration;
    }

    public boolean validateRefreshToken(String refreshToken) {
        return repository.findByRefreshToken(refreshToken)
            .filter(admin -> !admin.isDeletedFlg() && admin.getRefreshTokenExpiration().isAfter(LocalDateTime.now()))
            .isPresent();
    }

    public AuthResponse refresh(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        Admin admin = repository.findByRefreshToken(refreshToken).orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));
        String token = jwtService.generateToken(admin);
        AuthResponse authResponse = new AuthResponse(token, jwtService.getExpirationTime(), refreshToken);
        return authResponse;
    }

    public AdminResponse convertToDTO(Admin admin) {
        AdminResponse adminResponse = new AdminResponse(admin.getId(), admin.getFullName(), admin.getUsername(), admin.getEmail(), admin.getRole().name(), admin.getRoleMenu());
        return adminResponse;
    }
}
