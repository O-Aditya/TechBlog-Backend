package com.adityacode.Blog_App.controllers;


import com.adityacode.Blog_App.domain.dtos.CreateTagRequest;
import com.adityacode.Blog_App.domain.dtos.TagResponse;
import com.adityacode.Blog_App.domain.entities.Tag;
import com.adityacode.Blog_App.mapper.TagMapper;
import com.adityacode.Blog_App.repository.TagRepository;
import com.adityacode.Blog_App.services.TagServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagServices tagServices;
    private final TagMapper  tagMapper;



    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){

        List<Tag> tags = tagServices.getAllTags();
        List<TagResponse> tagResponses = tags.stream().map(tagMapper :: toTagResponse).toList();
        return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> addTags(@RequestBody CreateTagRequest  createTagRequest){

        List<Tag> savedTag = tagServices.addTags(createTagRequest.getNames());
        List<TagResponse> createdTagResponse = savedTag.stream().map(tagMapper :: toTagResponse).toList();

        return  new ResponseEntity<>(
                createdTagResponse,
                HttpStatus.CREATED
        );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id){
        tagServices.deleteTags(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
