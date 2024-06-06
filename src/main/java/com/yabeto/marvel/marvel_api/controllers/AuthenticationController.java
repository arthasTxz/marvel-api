package com.yabeto.marvel.marvel_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yabeto.marvel.marvel_api.dto.security.LoginRequest;
import com.yabeto.marvel.marvel_api.dto.security.LoginResponse;
import com.yabeto.marvel.marvel_api.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(
        @RequestBody @Valid  LoginRequest loginRequest
    ){
        return ResponseEntity.ok(this.authenticationService.authenticate(loginRequest));
    }

    @PreAuthorize("permitAll")
    @PostMapping("/logout")
    public void logout() throws Exception{
        authenticationService.logout();
    }
}
