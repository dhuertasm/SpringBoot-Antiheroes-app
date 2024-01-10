package com.dahuertasm.springbootsuperheroes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dahuertasm.springbootsuperheroes.antiHero.service.UserDetailsServiceImpl;
import com.dahuertasm.springbootsuperheroes.exceptions.AuthEntryPoint;
import com.dahuertasm.springbootsuperheroes.filters.AuthenticationFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailService;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();

    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors((cors) -> cors.disable()).csrf((csrf) -> csrf.disable())
                .sessionManagement(
                        (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((request) -> request.requestMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll())
                .authorizeHttpRequests(
                        (request) -> request.requestMatchers(HttpMethod.POST, "api/v1/users/register").permitAll())
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST, "api/authenticate/login")
                                .permitAll().anyRequest().authenticated());

        return http.build();

    }
}
