package com.yabeto.marvel.marvel_api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String username,
    @NotBlank String password
) {

}
