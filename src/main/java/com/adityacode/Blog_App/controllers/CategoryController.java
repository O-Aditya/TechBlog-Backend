package com.adityacode.Blog_App.controllers;


import com.adityacode.Blog_App.domain.dtos.CategoryDto;
import com.adityacode.Blog_App.domain.dtos.Createcategoryrequest;
import com.adityacode.Blog_App.domain.entities.Category;
import com.adityacode.Blog_App.mapper.CategoryMapper;
import com.adityacode.Blog_App.repository.CategoryRepository;
import com.adityacode.Blog_App.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

//    public CategoryController(CategoryMapper categoryMapper, CategoryService categoryService) {
//        this.categoryMapper = categoryMapper;
//        this.categoryService = categoryService;
//    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        List<CategoryDto> categories = categoryService.listCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody Createcategoryrequest createcategoryrequest){
        Category categoryToCreate = categoryMapper.toEntity(createcategoryrequest);
        Category savedCategory = categoryService.createCategory(categoryToCreate);

        return new ResponseEntity<>(
                categoryMapper.toDto(savedCategory),
                HttpStatus.CREATED
        );

    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
