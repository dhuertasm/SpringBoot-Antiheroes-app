package com.dahuertasm.springbootsuperheroes.antiHero.service;

import java.util.Optional;
import java.util.UUID;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dahuertasm.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.repository.AntiHeroRepository;
import com.dahuertasm.springbootsuperheroes.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AntiHeroService {

    private final AntiHeroRepository repo;

    private AntiHeroEntity findOrThrow(final Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Anti-Hero by id " + id + "not found"));
    }

    public Iterable<AntiHeroEntity> findAllAntiHeroes() {
        return repo.findAll();
    }

    public AntiHeroEntity findAntiHeroById(Long id) {
        return findOrThrow(id);
    }

    public AntiHeroEntity findByFirstName(String firtName) {
        Optional<AntiHeroEntity> entity = repo.findByFirstName(firtName);
        return entity.orElseThrow();
    }

    public void removeAntiHeroById(Long id) {
        repo.deleteById(id);
    }

    public AntiHeroEntity addAntiHeroEntity(AntiHeroEntity antiHero) {
        return repo.save(antiHero);
    }

    public void UpdateAntiHero(Long id, AntiHeroEntity antiHero) {
        findOrThrow(id);
        repo.save(antiHero);
    }

}
