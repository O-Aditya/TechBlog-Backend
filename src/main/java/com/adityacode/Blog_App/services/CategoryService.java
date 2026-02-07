package com.adityacode.Blog_App.services;


import com.adityacode.Blog_App.domain.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {
    List<Category> listCategories();

    Category createCategory(Category category);

    void deleteCategory(UUID id);

    Category findCategoryById(UUID id);
}
