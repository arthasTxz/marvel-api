package com.yabeto.marvel.marvel_api.repositories;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.yabeto.marvel.marvel_api.dto.ComicDto;
import com.yabeto.marvel.marvel_api.dto.MyPageable;
import com.yabeto.marvel.marvel_api.integration.marvel.ComicMapper;
import com.yabeto.marvel.marvel_api.integration.marvel.MarvelApiConfig;
import com.yabeto.marvel.marvel_api.services.HttpClientService;

import jakarta.annotation.PostConstruct;

@Repository
public class ComicRepository {

     @Autowired
    private MarvelApiConfig marvelApiConfig;

    @Autowired
    private HttpClientService httpClientService;
    @Value("${integretion.marvel.base-path}")
    private String basePath;
    private String comicPath;

    @PostConstruct
    private void setPath(){
        this.comicPath = basePath.concat("/").concat("comics");
    }

    public List<ComicDto> findAll(MyPageable pageable, Long characterId) {
        Map<String, String> marvelQueryParams = getQueryParamsForFindAll(pageable, characterId);

        JsonNode response = httpClientService.doGet(comicPath, marvelQueryParams, JsonNode.class);
        return ComicMapper.toDtoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(MyPageable pageable, Long characterId) {
        Map<String, String> marvelQueryParams = marvelApiConfig.getAuthenticationQueryParams();
        marvelQueryParams.put("offset", Long.toString(pageable.offset()));
        marvelQueryParams.put("limit", Long.toString(pageable.limit()));

        if(characterId != null && characterId.longValue() > 0){
            marvelQueryParams.put("characters", Long.toString(characterId));
        }

        return marvelQueryParams;

    }

    public ComicDto findById(Long comicId) {
        Map<String, String> marvelQueryParams = marvelApiConfig.getAuthenticationQueryParams();
        String finalUrl = comicPath.concat("/").concat(Long.toString(comicId));
        JsonNode response = httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);
        return ComicMapper.toDtoList(response).get(0);
    }

}
