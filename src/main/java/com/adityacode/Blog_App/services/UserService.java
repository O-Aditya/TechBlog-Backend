package com.adityacode.Blog_App.services;


import com.adityacode.Blog_App.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    User getUserById(UUID userId);
}
