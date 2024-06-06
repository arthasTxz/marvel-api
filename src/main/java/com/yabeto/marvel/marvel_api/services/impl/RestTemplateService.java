package com.yabeto.marvel.marvel_api.services.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.yabeto.marvel.marvel_api.exception.ApiErrorException;
import com.yabeto.marvel.marvel_api.services.HttpClientService;

@Service
public class RestTemplateService implements HttpClientService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T doGet(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity<T> httpEntity = new HttpEntity<T>(getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value()){
            String message = String.format("Error consumiendo endpoint [ {} - {} ], codigo de respuesta es: {}",
            HttpMethod.GET, endpoint, response.getStatusCode() );
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

    private String buildFinalUrl(String endpoint, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endpoint);
        
        if(queryParams != null){

            for(Map.Entry<String, String> entry : queryParams.entrySet()){
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }

        String finalUrl = builder.build().toUriString();
        return finalUrl;
    }

    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public <T, R> T doPost(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.POST, httpEntity, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value() || response.getStatusCode().value() != HttpStatus.CREATED.value()){
            String message = String.format("Error consumiendo endpoint [ {} - {} ], codigo de respuesta es: {}",
            HttpMethod.POST, endpoint, response.getStatusCode() );
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

    @Override
    public <T, R> T doPut(String endpoint, Map<String, String> queryParams, Class<T> responseType, R bodyRequest) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity httpEntity = new HttpEntity(bodyRequest, getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.PUT, httpEntity, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value()){
            String message = String.format("Error consumiendo endpoint [ {} - {} ], codigo de respuesta es: {}",
            HttpMethod.PUT, endpoint, response.getStatusCode() );
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

    @Override
    public <T> T doDelete(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String finalUrl = buildFinalUrl(endpoint, queryParams);

        HttpEntity<T> httpEntity = new HttpEntity<T>(getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUrl, HttpMethod.DELETE, httpEntity, responseType);

        if(response.getStatusCode().value() != HttpStatus.OK.value()){
            String message = String.format("Error consumiendo endpoint [ {} - {} ], codigo de respuesta es: {}",
            HttpMethod.DELETE, endpoint, response.getStatusCode() );
            throw new ApiErrorException(message);
        }
        return response.getBody();
    }

}
