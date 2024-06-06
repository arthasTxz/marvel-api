package com.yabeto.marvel.marvel_api.dto;

public record ComicDto(
    Long id,
    String title,
    String description,
    String modified,
    String resourceURI,
    ThumbnailDto thumbnail
) {

}
