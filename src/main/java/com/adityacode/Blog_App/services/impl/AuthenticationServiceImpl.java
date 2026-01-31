package com.adityacode.Blog_App.services.impl;

import com.adityacode.Blog_App.security.JwtUtils;
import com.adityacode.Blog_App.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails authenticate(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtils.generateToken(userDetails);
    }

    @Override
    public UserDetails validateToken(String token) {
        // Validation is primarily handled in the filter, but if we need a method here:
        String username = jwtUtils.extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }
}
