package com.yabeto.marvel.marvel_api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.yabeto.marvel.marvel_api.dto.GetUserInteractionLogDto;
import com.yabeto.marvel.marvel_api.mapper.UserInteractionLogMapper;
import com.yabeto.marvel.marvel_api.repositories.UserInteractionLogRepository;
import com.yabeto.marvel.marvel_api.services.UserInteractionLogService;

@Service
public class UserInteractionLogServiceImpl implements UserInteractionLogService{

    @Autowired
    private UserInteractionLogRepository userInteractionLogRepository;

    @Override
    public Page<GetUserInteractionLogDto> findAll(Pageable pageable) {
        return userInteractionLogRepository.findAll(pageable)
        .map(UserInteractionLogMapper::toDto);
    }

    @Override
    public Page<GetUserInteractionLogDto> findByUsername(Pageable pageable, String username) {
        return userInteractionLogRepository.findByUsername(pageable, username)
        .map(UserInteractionLogMapper::toDto);
    }

}
