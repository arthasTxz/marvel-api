package com.yabeto.marvel.marvel_api.integration.marvel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.yabeto.marvel.marvel_api.dto.ComicDto;
import com.yabeto.marvel.marvel_api.dto.ThumbnailDto;

public class ComicMapper {

    public static List<ComicDto> toDtoList(JsonNode rootNode){
        ArrayNode resultsNode = getResultsNode(rootNode);

        List<ComicDto> comics = new ArrayList<>();
        resultsNode.elements().forEachRemaining(each -> {
            comics.add(ComicMapper.toDto(each));
        });

        return comics;
    }

    private static ComicDto toDto(JsonNode comic) {
        if(comic == null){
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        ThumbnailDto thumbnailDtodto = ThumbnailMapper.toDto(comic.get("thumbnail"));

        ComicDto dto = new ComicDto(
            comic.get("id").asLong()
            , comic.get("title").asText()
            , comic.get("description").asText()
            , comic.get("modified").asText()
            , comic.get("resourceURI").asText()
            , thumbnailDtodto);

        return dto;
    }

    private static ArrayNode getResultsNode(JsonNode rootNode){
        if(rootNode == null){
            throw new IllegalArgumentException("El nodo json no puede ser null");
        }

        JsonNode dataNode = rootNode.get("data");
        return (ArrayNode) dataNode.get("results");
    }

}
