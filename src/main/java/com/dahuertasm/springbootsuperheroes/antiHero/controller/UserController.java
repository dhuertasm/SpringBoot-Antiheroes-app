package com.dahuertasm.springbootsuperheroes.antiHero.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dahuertasm.springbootsuperheroes.antiHero.dto.UserDto;
import com.dahuertasm.springbootsuperheroes.antiHero.entity.UserEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable("id") Long id) {
        return service.findUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        service.removeUserById(id);
    }

    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserDto userDto) {
        try {

            return service.createUser(userDto);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    public void putUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        service.UpdateUser(id, userDto.email(), userDto.password());
    }

}
