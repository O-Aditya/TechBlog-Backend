package com.adityacode.Blog_App.domain.dtos;

import com.adityacode.Blog_App.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePostRequestDto {

    private UUID id;

    @NotBlank(message = "Title is required")
    @Size(min = 3 ,  max = 100 , message = "Title must be between {min} and {max} Character")
    private String title;

    @NotBlank(message = "content should not be blank")
    @Size(min = 10 , max = 5000 , message = "Content must be between {min} and {max} Character")
    private String content;

    @NotNull(message = "Category Id is required")
    private UUID categoryId;

    @Builder.Default
    @Size(max = 10 , message = "Maximum {max} tags allowed")
    private Set<UUID> tagId = new HashSet<>();

    @NotNull(message = "status is required")
    private PostStatus status;
}
