package com.dahuertasm.springbootsuperheroes.antiHero.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.coyote.BadRequestException;
import org.hibernate.mapping.Collection;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dahuertasm.springbootsuperheroes.antiHero.dto.UserDto;
import com.dahuertasm.springbootsuperheroes.antiHero.entity.UserEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.repository.UserRepository;
import com.dahuertasm.springbootsuperheroes.exceptions.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<UserEntity> findAllUser() {
        var userEntityList = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return userEntityList;
    }

    public UserEntity findUserById(Long id) {
        Optional<UserEntity> user = repository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException("User with id " + id.toString() + "not found");
        }
    }

    public UserEntity createUser(UserDto userDto) throws BadRequestException {
        if (userDto.password().isBlank()) {
            throw new IllegalArgumentException("Password is required", null);
        }

        if (userDto.email().isBlank()) {
            throw new IllegalArgumentException("email is required", null);
        }

        Optional<UserEntity> user = repository.findByEmail(userDto.email());
        if (user.isPresent()) {
            throw new BadRequestException("Email " + userDto.email() + " taken");
        }

        String hashPassword = new BCryptPasswordEncoder().encode(userDto.password());// BCrypt.hashpw(password, "salt");
        UserEntity newUser = new UserEntity();
        newUser.setPassword(hashPassword);
        newUser.setUsername(userDto.username());
        newUser.setRole(userDto.role());
        newUser.setEmail(userDto.email());

        return repository.save(newUser);

    }

    public void removeUserById(Long id) {
        findUserById(id);
        repository.deleteById(id);
    }

    public void UpdateUser(Long id, String email, String password) {
        var user = findUserById(id);
        user.setEmail(email);
        var hashPassword = new BCryptPasswordEncoder().encode(password);// BCrypt.hashpw(password, "salt");
        user.setPassword(hashPassword);

        repository.save(user);
    }

}
