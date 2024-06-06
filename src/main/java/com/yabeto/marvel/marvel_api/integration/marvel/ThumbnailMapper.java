package com.yabeto.marvel.marvel_api.integration.marvel;

import com.fasterxml.jackson.databind.JsonNode;
import com.yabeto.marvel.marvel_api.dto.ThumbnailDto;

public class ThumbnailMapper {

    public static ThumbnailDto toDto(JsonNode thumbnailNode){
        if(thumbnailNode == null){
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        ThumbnailDto dto = new ThumbnailDto(
            thumbnailNode.get("path").asText(), thumbnailNode.get("extension").asText());

        return dto;
    }
}
