package com.adityacode.Blog_App.services;

import com.adityacode.Blog_App.domain.CreatePostRequest;
import com.adityacode.Blog_App.domain.UpdatePostRequest;
import com.adityacode.Blog_App.domain.entities.Post;
import com.adityacode.Blog_App.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PostService {
    Page<Post> ListAllPosts(UUID categoryId, UUID tagId, Pageable pageable);

    List<Post> ListAllDraftPosts(UUID userId);

    Post createPost(User user, CreatePostRequest createPostRequestDto);

    Post GetPost(UUID postID);

    Post updatePost(UUID postId, UpdatePostRequest updatePostRequestDto);

    void deletePost(UUID postId);
}
