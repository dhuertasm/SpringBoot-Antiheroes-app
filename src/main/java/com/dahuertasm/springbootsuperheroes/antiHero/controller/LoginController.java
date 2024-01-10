package com.dahuertasm.springbootsuperheroes.antiHero.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.dahuertasm.springbootsuperheroes.antiHero.dto.LoginDto;
import com.dahuertasm.springbootsuperheroes.antiHero.service.JwtService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RestController
@RequestMapping("api/authenticate")
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> postMethodName(@RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(loginDto.username(),
                loginDto.password());

        Authentication auth = authenticationManager.authenticate(creds);

        String jwts = jwtService.genToken(auth.getName());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();

    }

}
