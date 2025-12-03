package com.libraryproject.School.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Year is required")
    @Min(value = 1, message = "Year must be greater than 0")
    private Integer year;

    @NotNull(message = "Pages are required")
    @Min(value = 1, message = "Pages must be greater than 0")
    private Integer pages;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Publisher ID is required")
    private Long publisherId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Author IDs are required")
    private Set<Long> authorIds;

    public BookDTO() {}

    public BookDTO(Long id, String title, String isbn, Integer year, Integer pages, String language,
                   Long publisherId, Long categoryId, Set<Long> authorIds) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.pages = pages;
        this.language = language;
        this.publisherId = publisherId;
        this.categoryId = categoryId;
        this.authorIds = authorIds;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getYear() {
    return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Set<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {
        this.authorIds = authorIds;
    }
}
