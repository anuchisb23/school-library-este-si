package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.AuthorDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorDTO save(AuthorDTO authorDTO);
    List<AuthorDTO> findAll();
    Optional<AuthorDTO> findById(Long id);
    void deleteById(Long id);

    List<AuthorDTO> findByFirstName(String firstName);
    List<AuthorDTO> findByLastName(String lastName);
    Optional<AuthorDTO> findByFullName(String firstName, String lastName);
    List<AuthorDTO> findByBirthDate(LocalDate birthDate);
    List<AuthorDTO> findAuthorsWithBooks();
}
