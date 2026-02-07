package com.adityacode.Blog_App.domain.dtos;


import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {

    private UUID id;
    private String name;
}
