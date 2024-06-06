package com.yabeto.marvel.marvel_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yabeto.marvel.marvel_api.dto.CharacterDto;
import com.yabeto.marvel.marvel_api.dto.MyPageable;
import com.yabeto.marvel.marvel_api.services.CharacterService;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PreAuthorize("hasAuthority('character:read-all')")
    @GetMapping
    public ResponseEntity<List<CharacterDto>> findAll(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) int[] comics,
        @RequestParam(required = false) int[] series,
        @RequestParam(defaultValue = "0") long offset,
        @RequestParam(defaultValue = "10") long limit
    ){
        MyPageable pageable = new MyPageable(offset, limit);
        return ResponseEntity.ok(characterService.findAll(pageable, name, comics, series));
    }

    @PreAuthorize("hasAuthority('character:read-detail')")
    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterDto.CharacterInfoDto> findInfoById(@PathVariable Long characterId){
        return ResponseEntity.ok(characterService.findInfoById(characterId));
    }
}
