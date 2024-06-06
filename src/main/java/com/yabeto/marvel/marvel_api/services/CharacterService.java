package com.yabeto.marvel.marvel_api.services;

import java.util.List;

import com.yabeto.marvel.marvel_api.dto.CharacterDto.CharacterInfoDto;
import com.yabeto.marvel.marvel_api.dto.CharacterDto;
import com.yabeto.marvel.marvel_api.dto.MyPageable;

public interface CharacterService {

    List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series);

    CharacterInfoDto findInfoById(Long characterId);

}
