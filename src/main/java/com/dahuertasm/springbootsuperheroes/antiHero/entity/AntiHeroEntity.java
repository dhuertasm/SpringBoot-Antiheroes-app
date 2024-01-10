package com.dahuertasm.springbootsuperheroes.antiHero.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "anti_hero_entity")
@AllArgsConstructor
@NoArgsConstructor
public class AntiHeroEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NotNull(message = "First name is required")
    private String firstName;
    private String lastName;
    private String house;
    private String knownAs;
    private String createAt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z").format(new Date());

}
