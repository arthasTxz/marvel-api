package com.yabeto.marvel.marvel_api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yabeto.marvel.marvel_api.dto.ComicDto;
import com.yabeto.marvel.marvel_api.dto.MyPageable;
import com.yabeto.marvel.marvel_api.repositories.ComicRepository;
import com.yabeto.marvel.marvel_api.services.ComicService;

@Service
public class ComicServiceImpl implements ComicService{

    @Autowired
    private ComicRepository comicRepository;

    @Override
    public List<ComicDto> findAll(MyPageable pageable, Long characterId) {
        return comicRepository.findAll(pageable, characterId);
    }

    @Override
    public ComicDto findById(Long comicId) {
        return comicRepository.findById(comicId);
    }

}
