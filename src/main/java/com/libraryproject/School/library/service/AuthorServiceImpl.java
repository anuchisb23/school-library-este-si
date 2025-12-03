package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.AuthorDTO;
import com.libraryproject.School.library.model.Author;
import com.libraryproject.School.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // ===================== MÉTODOS CON DTO =====================

    @Override
    public AuthorDTO save(AuthorDTO dto) {
        Author author = convertToEntity(dto);
        Author saved = authorRepository.save(author);
        return convertToDTO(saved);
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> findById(Long id) {
        return authorRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDTO> findByFirstName(String firstName) {
        return authorRepository.searchByName(firstName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> findByLastName(String lastName) {
        return authorRepository.searchByName(lastName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> findByFullName(String firstName, String lastName) {
        return authorRepository.findByFullName(firstName, lastName)
                .map(this::convertToDTO);
    }

    @Override
    public List<AuthorDTO> findByBirthDate(LocalDate birthDate) {
        return authorRepository.findAuthorsBornAfter(birthDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> findAuthorsWithBooks() {
        return authorRepository.findAuthorsWithBooks().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ===================== CONVERSIÓN =====================

    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getNationality(),
                author.getBirthDate()
        );
    }

    private Author convertToEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setNationality(dto.getNationality());
        author.setBirthDate(dto.getBirthDate());
        return author;
    }
}
