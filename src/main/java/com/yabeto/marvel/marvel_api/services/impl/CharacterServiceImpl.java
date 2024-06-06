package com.yabeto.marvel.marvel_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yabeto.marvel.marvel_api.dto.CharacterDto;
import com.yabeto.marvel.marvel_api.dto.CharacterDto.CharacterInfoDto;
import com.yabeto.marvel.marvel_api.repositories.CharacterRepository;
import com.yabeto.marvel.marvel_api.dto.MyPageable;
import com.yabeto.marvel.marvel_api.services.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService{

    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public List<CharacterDto> findAll(MyPageable pageable, String name, int[] comics, int[] series) {
        return characterRepository.findAll(pageable, name, comics, series);
    }

    @Override
    public CharacterInfoDto findInfoById(Long characterId) {
        return characterRepository.findInfoById(characterId);
    }

}
