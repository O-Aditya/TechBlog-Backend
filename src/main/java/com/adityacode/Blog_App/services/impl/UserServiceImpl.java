package com.adityacode.Blog_App.services.impl;

import com.adityacode.Blog_App.domain.entities.User;
import com.adityacode.Blog_App.repository.UserRepository;
import com.adityacode.Blog_App.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not Found"));
    }
}
