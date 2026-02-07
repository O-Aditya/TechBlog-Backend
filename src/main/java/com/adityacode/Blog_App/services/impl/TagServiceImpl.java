package com.adityacode.Blog_App.services.impl;

import com.adityacode.Blog_App.domain.entities.Tag;
import com.adityacode.Blog_App.repository.TagRepository;
import com.adityacode.Blog_App.services.TagServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagServices {

    private final TagRepository tagRepository;


    @Override
    public List<Tag> getAllTags() {

        return tagRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public List<Tag> addTags(Set<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return Collections.emptyList();
        }

        // Normalize input
        Set<String> normalizedNames = tagNames.stream()
                .map(name -> name.toLowerCase().trim())
                .collect(Collectors.toSet());

        // Find existing tags
        List<Tag> existingTags = tagRepository.findByNameIn(normalizedNames);

        Set<String> existingNames = existingTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        // Create and save new tags
        List<Tag> newTags = normalizedNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> Tag.builder()
                        .name(name)
                        .posts(new HashSet<>())
                        .build())
                .toList();

        if (!newTags.isEmpty()) {
            newTags = tagRepository.saveAll(newTags);
        }

        // Combine and return
        return Stream.concat(existingTags.stream(), newTags.stream())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTags(UUID id) {
        tagRepository.findById(id).ifPresent(
                tag -> {
                    if(!tag.getPosts().isEmpty()){
                        throw new IllegalStateException("Cannot delete tags because tag posts are not empty");
                    }
                    tagRepository.delete(tag);
                });
    }

    @Override
    public Tag findTagById(UUID id) {

        return tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find tag with id " + id));
    }

    @Override
    public List<Tag> findTagByIds(Set<UUID> ids) {
        List<Tag> foundTag = tagRepository.findAllById(ids);
        if(foundTag.size() != ids.size()){
            throw new EntityNotFoundException("Not found any tags with ids " + ids);
        }

        return foundTag;
    }
}
