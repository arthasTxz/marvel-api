package com.yabeto.marvel.marvel_api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yabeto.marvel.marvel_api.entities.UserInteractionLog;

public interface UserInteractionLogRepository extends JpaRepository<UserInteractionLog, Long>{

    Page<UserInteractionLog> findByUsername(Pageable pageable, String username);

}
