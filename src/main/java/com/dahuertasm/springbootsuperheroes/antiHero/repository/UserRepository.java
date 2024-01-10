package com.dahuertasm.springbootsuperheroes.antiHero.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.dahuertasm.springbootsuperheroes.antiHero.entity.UserEntity;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

}
