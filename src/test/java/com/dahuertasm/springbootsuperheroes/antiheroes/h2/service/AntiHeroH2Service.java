package com.dahuertasm.springbootsuperheroes.antiheroes.h2.service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.dahuertasm.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.repository.AntiHeroRepository;
import com.dahuertasm.springbootsuperheroes.antiHero.service.AntiHeroService;
import com.dahuertasm.springbootsuperheroes.exceptions.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;

@DataJpaTest
public class AntiHeroH2Service {
    @Autowired
    private AntiHeroRepository repo;
    private AntiHeroService service;

    @BeforeEach
    public void setUp() {
        service = new AntiHeroService(repo);
    }

    @Test
    public void shouldFindAllAntiHero() {
        AntiHeroEntity antiHero = new AntiHeroEntity();
        antiHero.setFirstName("daniel");
        antiHero.setHouse("house");
        antiHero.setLastName("lastname");
        antiHero.setKnownAs("elmejor");

        service.addAntiHeroEntity(antiHero);

        Iterable<AntiHeroEntity> antiHeroList = service.findAllAntiHeroes();
        AntiHeroEntity saveAntiHero = antiHeroList.iterator().next();
        assertThat(saveAntiHero).isNotNull();
    }

    @Test
    @Sql("createAntiHero.sql")
    public void shouldFindAntiHeroByFirstName() {
        AntiHeroEntity antihero = service.findByFirstName("daniel");
        assertThat(antihero).isNotNull();
    }

    @Test
    @Sql("createAntiHero.sql")
    public void shouldUpdateAntiHero() {
        AntiHeroEntity savedAntiHeroEntity = service.findByFirstName("daniel");
        savedAntiHeroEntity.setFirstName("francisco");
        service.UpdateAntiHero(savedAntiHeroEntity.getId(), savedAntiHeroEntity);

        AntiHeroEntity foundAntiHero = service.findAntiHeroById(savedAntiHeroEntity.getId());

        assertThat(foundAntiHero.getFirstName()).isEqualTo("francisco");
    }

    @Test
    @Sql("createAntiHero.sql")
    public void shouldDeleteAntiHero() {
        assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() {
                AntiHeroEntity antiHero = service.findByFirstName("daniel");
                service.removeAntiHeroById(antiHero.getId());

                AntiHeroEntity foundAntiHero = service.findAntiHeroById(antiHero.getId());
                assertThat(foundAntiHero).isNull();

            }

        });

    }

}
