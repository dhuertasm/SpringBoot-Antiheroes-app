package com.dahuertasm.springbootsuperheroes.antiHero.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.dahuertasm.springbootsuperheroes.antiHero.entity.UserEntity;
import com.dahuertasm.springbootsuperheroes.antiHero.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<UserEntity> user = repository.findByUsername(username);
        UserBuilder builder = null;
        if (user.isPresent()) {
            UserEntity currentUser = user.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getRole());
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        return builder.build();
    }

}
