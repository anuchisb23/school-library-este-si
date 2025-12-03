package com.libraryproject.School.library.graphql;

import com.libraryproject.School.library.dto.CategoryDTO;
import com.libraryproject.School.library.service.CategoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoryGraphql {

    private final CategoryService categoryService;

    public CategoryGraphql(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.findAll();
    }

    @QueryMapping
    public CategoryDTO getCategoryById(@Argument Long id) {
        return categoryService.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }

    @MutationMapping
    public CategoryDTO createCategory(@Argument("category") CategoryDTO categoryDTO) {
        return categoryService.save(categoryDTO);
    }

    @MutationMapping
    public CategoryDTO updateCategory(@Argument Long id, @Argument("category") CategoryDTO categoryDTO) {
        categoryDTO.setId(id);
        return categoryService.save(categoryDTO);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        categoryService.deleteById(id);
        return true;
    }
}
