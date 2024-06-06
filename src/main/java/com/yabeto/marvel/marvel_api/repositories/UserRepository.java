package com.yabeto.marvel.marvel_api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.yabeto.marvel.marvel_api.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

    Optional<User> findByUsername(String username);
}
