package com.adityacode.Blog_App.repository;

import com.adityacode.Blog_App.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<User, UUID> {
}
