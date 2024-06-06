package com.yabeto.marvel.marvel_api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yabeto.marvel.marvel_api.dto.GetUserInteractionLogDto;

public interface UserInteractionLogService {

    Page<GetUserInteractionLogDto> findAll(Pageable pageable);

    Page<GetUserInteractionLogDto> findByUsername(Pageable pageable, String username);

}
