package com.dahuertasm.springbootsuperheroes.antiHero.dto;

public record UserDto(
        String username,
        String password,
        String email,
        String role) {

}
