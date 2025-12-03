package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.CategoryDTO;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDTO save(CategoryDTO category);
    List<CategoryDTO> findAll();
    Optional<CategoryDTO> findById(Long id);
    void deleteById(Long id);
}
