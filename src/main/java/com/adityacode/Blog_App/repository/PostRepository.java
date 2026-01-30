package com.adityacode.Blog_App.repository;

import com.adityacode.Blog_App.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {


}
