package com.adityacode.Blog_App.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adityacode.Blog_App.domain.CreatePostRequest;
import com.adityacode.Blog_App.domain.PostStatus;
import com.adityacode.Blog_App.domain.UpdatePostRequest;
import com.adityacode.Blog_App.domain.entities.Category;
import com.adityacode.Blog_App.domain.entities.Post;
import com.adityacode.Blog_App.domain.entities.Tag;
import com.adityacode.Blog_App.domain.entities.User;
import com.adityacode.Blog_App.repository.PostRepository;
import com.adityacode.Blog_App.services.CategoryService;
import com.adityacode.Blog_App.services.PostService;
import com.adityacode.Blog_App.services.TagServices;

import com.adityacode.Blog_App.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagServices tagServices;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<Post> ListAllPosts(UUID categoryId, UUID tagId, Pageable pageable) {

        if (categoryId != null && tagId != null) {
            Category category = categoryService.findCategoryById(categoryId);
            Tag tag = tagServices.findTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTags(PostStatus.PUBLISHED, category, tag, pageable);
        }

        if (categoryId != null) {
            Category category = categoryService.findCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category, pageable);
        }

        if (tagId != null) {
            Tag tag = tagServices.findTagById(tagId);
            return postRepository.findAllByStatusAndTags(PostStatus.PUBLISHED, tag, pageable);
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED, pageable);
    }

    @Override
    public List<Post> ListAllDraftPosts(UUID userId) {
        User user = userService.getUserById(userId);
        return postRepository.findAllByStatusAndAuthor(PostStatus.DRAFT, user);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setAuthor(user);
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));
        Category category = categoryService.findCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagServices.findTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    public Post GetPost(UUID postId) {

        return postRepository.findByIdWithTags(postId).orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

    }

    @Override
    @Transactional
    public Post updatePost(UUID postId, UpdatePostRequest updatePostRequestDto) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with id " + postId + " not found!"));

        existingPost.setTitle(updatePostRequestDto.getTitle());
        existingPost.setContent(updatePostRequestDto.getContent());
        existingPost.setStatus(updatePostRequestDto.getStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequestDto.getContent()));

        UUID updateCategoryId = updatePostRequestDto.getCategoryId();
        if (!existingPost.getCategory().getId().equals(updateCategoryId)) {
            Category category = categoryService.findCategoryById(updateCategoryId);
            existingPost.setCategory(category);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        Set<UUID> updatedTagIds = updatePostRequestDto.getTagIds();
        if (!existingTagIds.equals(updatedTagIds)) {
            List<Tag> newTags = tagServices.findTagByIds(updatedTagIds);
            existingPost.setTags(new HashSet<>(newTags));

        }

        return postRepository.save(existingPost);
    }

    @Override
    @Transactional
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post with id " + postId + " not found!"));
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content) {
        final int Word_Per_Min = 200;
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / Word_Per_Min);

    }
}
