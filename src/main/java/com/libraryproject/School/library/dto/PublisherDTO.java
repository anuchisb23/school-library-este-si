package com.libraryproject.School.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public class PublisherDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9\\-\\+]{7,15}$", message = "Phone must be valid")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    // 👇 IDs de libros relacionados (puede ser opcional)
    private List<Long> bookIds;

    // Constructores
    public PublisherDTO() {}

    public PublisherDTO(Long id, String name, String address, String phone, String email, List<Long> bookIds) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.bookIds = bookIds;
    }
    
    // Getters y Setters tradicionales
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }
    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
