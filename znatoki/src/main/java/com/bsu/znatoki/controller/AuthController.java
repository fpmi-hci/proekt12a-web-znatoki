package com.bsu.znatoki.controller;

import com.bsu.znatoki.dto.LoginDto;
import com.bsu.znatoki.dto.PasswordResetDto;
import com.bsu.znatoki.dto.UserDto;
import com.bsu.znatoki.service.LoginService;
import com.bsu.znatoki.service.PasswordService;
import com.bsu.znatoki.service.RegistrationService;
import com.bsu.znatoki.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final PasswordService passwordService;
    private final JwtProvider jwtProvider;
    private final LoginService loginService;

    @PostMapping
    public void register(@Valid @RequestBody UserDto form) {
        registrationService.register(form);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        loginService.authorize(loginDto);
        String token = jwtProvider.generateToken(loginDto.getEmail());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/confirm/{hash}")
    public void confirm(@PathVariable String hash) {
        registrationService.confirmAndCreate(hash);
    }

    @PostMapping("/forgot_password")
    public void forgotPassword(@RequestParam String email) {
        passwordService.reset(email);
    }

    @PostMapping("/reset")
    public void forgotPassword(@Valid @RequestBody PasswordResetDto resetDto) {
        passwordService.confirmReset(resetDto);
    }
}
