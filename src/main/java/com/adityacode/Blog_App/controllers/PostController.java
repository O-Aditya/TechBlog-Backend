package com.adityacode.Blog_App.controllers;

import com.adityacode.Blog_App.domain.CreatePostRequest;
import com.adityacode.Blog_App.domain.UpdatePostRequest;
import com.adityacode.Blog_App.domain.dtos.CreatePostRequestDto;
import com.adityacode.Blog_App.domain.dtos.PostDto;
import com.adityacode.Blog_App.domain.dtos.UpdatePostRequestDto;
import com.adityacode.Blog_App.domain.entities.Post;
import com.adityacode.Blog_App.domain.entities.User;
import com.adityacode.Blog_App.mapper.PostMapper;
import com.adityacode.Blog_App.services.PostService;
import com.adityacode.Blog_App.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    // ...

    @GetMapping
    public ResponseEntity<Page<PostDto>> ListAllPost(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts = postService.ListAllPosts(categoryId, tagId, pageable);

        Page<PostDto> postDtos = posts.map(postMapper::toDto);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> ListAllDraftPost(@RequestAttribute UUID userId) {
        List<Post> posts = postService.ListAllDraftPosts(userId);
        List<PostDto> postDtos = posts.stream()
                .map(postMapper::toDto)
                .toList();

        return ResponseEntity.ok(postDtos);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> GetPost(@PathVariable UUID id) {
        Post post = postService.GetPost(id);
        PostDto postDto = postMapper.toDto(post);
        return ResponseEntity.ok(postDto);

    }

    @PostMapping
    public ResponseEntity<PostDto> CreatePost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId) {
        User loggedInUser = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(loggedInUser, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> UpdatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatedPost);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> DeletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
