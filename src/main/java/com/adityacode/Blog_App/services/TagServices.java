package com.adityacode.Blog_App.services;


import com.adityacode.Blog_App.domain.dtos.TagResponse;
import com.adityacode.Blog_App.domain.entities.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface TagServices {

    List<Tag> getAllTags();
    List<Tag> addTags(Set<String> tagsNames);

    void deleteTags(UUID id);

    Tag findTagById(UUID id);

    List<Tag> findTagByIds(Set<UUID> ids);
}
