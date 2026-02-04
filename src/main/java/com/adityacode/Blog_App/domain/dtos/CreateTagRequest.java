package com.adityacode.Blog_App.domain.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTagRequest {

    @NotEmpty(message = "Provide least one name ")
    @Size(max = 10 , message = "Maximum {max} tags allowed")
    private Set<String> names;

}
