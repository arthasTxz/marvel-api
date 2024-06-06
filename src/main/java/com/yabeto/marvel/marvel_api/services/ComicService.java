package com.yabeto.marvel.marvel_api.services;

import java.util.List;

import com.yabeto.marvel.marvel_api.dto.ComicDto;
import com.yabeto.marvel.marvel_api.dto.MyPageable;

public interface ComicService {

    List<ComicDto> findAll(MyPageable pageable, Long characterId);

    ComicDto findById(Long comicId);

}
