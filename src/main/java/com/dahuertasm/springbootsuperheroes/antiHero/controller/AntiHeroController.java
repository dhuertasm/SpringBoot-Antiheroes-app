package com.dahuertasm.springbootsuperheroes.antiHero.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dahuertasm.springbootsuperheroes.antiHero.dto.AntiHeroDto;
import com.dahuertasm.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.service.AntiHeroService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/anti-heroes")
public class AntiHeroController {

    private final AntiHeroService antiHeroService;
    private final ModelMapper modelMapper;

    private AntiHeroDto convertToDto(AntiHeroEntity entity) {
        return modelMapper.map(entity, AntiHeroDto.class);
    }

    private AntiHeroEntity converToEntity(AntiHeroDto dto) {
        return modelMapper.map(dto, AntiHeroEntity.class);
    }

    @GetMapping("/{id}")
    public AntiHeroEntity getAntiHeroDto(@PathVariable("id") Long id) {
        return antiHeroService.findAntiHeroById(id);
    }

    @PostMapping()
    public AntiHeroDto postMethodName(@Valid @RequestBody AntiHeroDto antiHero) {
        var entity = converToEntity(antiHero);
        var antiHEro = antiHeroService.addAntiHeroEntity(entity);
        return convertToDto(antiHEro);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAntiHero(@PathVariable("id") Long id) {
        antiHeroService.removeAntiHeroById(id);
    }

    @GetMapping()
    public List<AntiHeroDto> getAntiHeroes(Pageable pageable) {
        int toSkip = pageable.getPageSize() * pageable.getPageNumber();
        var antiHeroList = StreamSupport.stream(
                antiHeroService.findAllAntiHeroes().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return antiHeroList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    @PutMapping("/{id}")
    public void putAntiHEro(@PathVariable("id") Long id, @Valid @RequestBody AntiHeroDto antiHero) {
        // TODO: process PUT request

        // if (!id.equals(antiHero.getId())) {
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id does not
        // match");
        // }

        var antiHeroEntity = converToEntity(antiHero);
        antiHeroService.UpdateAntiHero(id, antiHeroEntity);
    }

}
