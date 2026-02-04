package com.adityacode.Blog_App.mapper;


import com.adityacode.Blog_App.domain.PostStatus;
import com.adityacode.Blog_App.domain.dtos.TagResponse;
import com.adityacode.Blog_App.domain.entities.Post;
import com.adityacode.Blog_App.domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {


    @Mapping(target = "postcount" , qualifiedByName = "calculatePostCount" , source = "posts"
    )
    TagResponse toTagResponse(Tag tag);

    @Named("calculatePostCount")
    default Integer CalculatePostCount(Set<Post> posts){
     if(posts.isEmpty()){
         return 0;
     }
    return (int)posts.stream()
             .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
             .count();
    }

}
