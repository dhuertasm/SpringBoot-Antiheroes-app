package com.dahuertasm.springbootsuperheroes.antiheroes.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.dahuertasm.springbootsuperheroes.antiHero.entity.AntiHeroEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.repository.AntiHeroRepository;
import com.dahuertasm.springbootsuperheroes.antiHero.service.AntiHeroService;

@ExtendWith(MockitoExtension.class)
public class AntiHeroServiceTest {

    @Mock
    private AntiHeroRepository antiHeroRepository;

    @InjectMocks
    private AntiHeroService antiHeroService;

    @Test
    void canAddAntiHero() {

        AntiHeroEntity antiHeroEntity = new AntiHeroEntity();
        antiHeroEntity.setFirstName("daniel");
        antiHeroEntity.setLastName("andres");
        antiHeroEntity.setHouse("DC");
        antiHeroEntity.setKnownAs("elmejor");

        // when
        antiHeroService.addAntiHeroEntity(antiHeroEntity);

        // then
        ArgumentCaptor<AntiHeroEntity> antiArgumentCaptor = ArgumentCaptor.forClass(AntiHeroEntity.class);

        verify(antiHeroRepository).save(
                antiArgumentCaptor.capture());

        AntiHeroEntity capturedAntiHero = antiArgumentCaptor.getValue();

        assertThat(capturedAntiHero).isEqualTo(antiHeroEntity);

    }

}
