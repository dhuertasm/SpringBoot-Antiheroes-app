package com.dahuertasm.springbootsuperheroes.antiHero.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dahuertasm.springbootsuperheroes.antiHero.entity.AntiHeroEntity;

@Repository
public interface AntiHeroRepository extends CrudRepository<AntiHeroEntity, Long> {

    Optional<AntiHeroEntity> findByFirstName(String firstName);

}
