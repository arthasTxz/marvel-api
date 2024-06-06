package com.yabeto.marvel.marvel_api.repositories;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.yabeto.marvel.marvel_api.dto.CharacterDto;
import com.yabeto.marvel.marvel_api.dto.CharacterDto.CharacterInfoDto;
import com.yabeto.marvel.marvel_api.integration.marvel.CharacterMapper;
import com.yabeto.marvel.marvel_api.integration.marvel.MarvelApiConfig;
import com.yabeto.marvel.marvel_api.services.HttpClientService;

import jakarta.annotation.PostConstruct;

import com.yabeto.marvel.marvel_api.dto.MyPageable;

@Repository
public class CharacterRepository {

    @Autowired
    private MarvelApiConfig marvelApiConfig;

    @Autowired
    private HttpClientService httpClientService;

    @Value("${integretion.marvel.base-path}")
    private String basePath;
    private String characterPath;

    @PostConstruct
    private void setPath(){
        this.characterPath = basePath.concat("/").concat("characters");
    }

    public List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series){
        Map<String, String> marvelQueryParams = getQueryParamsForFindAll(pageable, name, comics, series);

        JsonNode response = httpClientService.doGet(characterPath, marvelQueryParams, JsonNode.class);
        return CharacterMapper.toDtoList(response);

    };

    private Map<String, String> getQueryParamsForFindAll(MyPageable pageable, String name, int[] comics, int[] series) {
        
        Map<String, String> marvelQueryParams = marvelApiConfig.getAuthenticationQueryParams();

        marvelQueryParams.put("offset", Long.toString(pageable.offset()));
        marvelQueryParams.put("limit", Long.toString(pageable.limit()));

        if(StringUtils.hasText(name)){
            marvelQueryParams.put("name", name);
        }
        if(comics != null){
            String comicsAsString = this.joinIntArray(comics);
            marvelQueryParams.put("comics", comicsAsString);
        }
        if(series != null){
            String seriesAsString = this.joinIntArray(series);
            marvelQueryParams.put("series", seriesAsString);
        }

        return marvelQueryParams;
    }

    private String joinIntArray(int[] intArray) {
        List<String> stringArray = IntStream.of(intArray).boxed().map(each -> each.toString()).collect(Collectors.toList());
        return String.join(",", stringArray);
    }

    public CharacterInfoDto findInfoById(Long characterId) {
        Map<String, String> marvelQueryParams = marvelApiConfig.getAuthenticationQueryParams();
        String finalUrl = characterPath.concat("/").concat(Long.toString(characterId));

        JsonNode response = httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);
        return CharacterMapper.toInfoDtoList(response).get(0);
    }

    

}
