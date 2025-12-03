package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.CategoryDTO;
import com.libraryproject.School.library.model.Category;
import com.libraryproject.School.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // =================== MÉTODOS CON DTO ===================

    @Override
    public CategoryDTO save(CategoryDTO dto) {
        Category category = convertToEntity(dto);
        Category saved = categoryRepository.save(category);
        return convertToDTO(saved);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    // =================== CONVERSIÓN ===================

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }
}
