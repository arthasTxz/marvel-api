package com.yabeto.marvel.marvel_api.mapper;

import com.yabeto.marvel.marvel_api.dto.GetUserInteractionLogDto;
import com.yabeto.marvel.marvel_api.entities.UserInteractionLog;

public class UserInteractionLogMapper {

    public static GetUserInteractionLogDto toDto(UserInteractionLog entity){

        if(entity == null) return null;

        return new GetUserInteractionLogDto(
            entity.getId(),
             entity.getUrl(), 
             entity.getHttpMethod(), 
             entity.getUsername(), 
             entity.getTimestamp(), 
             entity.getRemoteAddress());
    }
}
