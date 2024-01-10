package com.dahuertasm.springbootsuperheroes.filters;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dahuertasm.springbootsuperheroes.antiHero.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String user = jwtService.getAuthUser(request);

        if (user != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                    java.util.Collections.emptyList());

            SecurityContextHolder.getContext()
                    .setAuthentication(auth);

        }

        filterChain.doFilter(request, response);

    }

}
