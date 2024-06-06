package com.yabeto.marvel.marvel_api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record GetUserInteractionLogDto(
    Long id,
    String url,
    String httpMethod,
    String username,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime timestamp,
    String remoteAddress
) {

}
