package com.yabeto.marvel.marvel_api.exception;

public record ApiErrorDto(
    String message,
    String backendMessage,
    String method,
    String url
) {

}
