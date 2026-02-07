package com.adityacode.Blog_App.mapper;

import com.adityacode.Blog_App.domain.CreatePostRequest;
import com.adityacode.Blog_App.domain.UpdatePostRequest;
import com.adityacode.Blog_App.domain.dtos.CreatePostRequestDto;
import com.adityacode.Blog_App.domain.dtos.PostDto;
import com.adityacode.Blog_App.domain.dtos.UpdatePostRequestDto;
import com.adityacode.Blog_App.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = { TagMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);

}
