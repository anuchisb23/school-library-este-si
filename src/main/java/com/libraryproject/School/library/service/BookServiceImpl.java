package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.BookDTO;
import com.libraryproject.School.library.model.Author;
import com.libraryproject.School.library.model.Book;
import com.libraryproject.School.library.model.Category;
import com.libraryproject.School.library.model.Publisher;
import com.libraryproject.School.library.repository.AuthorRepository;
import com.libraryproject.School.library.repository.BookRepository;
import com.libraryproject.School.library.repository.CategoryRepository;
import com.libraryproject.School.library.repository.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           PublisherRepository publisherRepository,
                           CategoryRepository categoryRepository,
                           AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDTO save(BookDTO dto) {
        Book book;

        if (dto.getId() != null) {
            // 🔹 UPDATE
            book = bookRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id " + dto.getId()));
        } else {
            // 🔹 CREATE
            book = new Book();
        }

        // mapear campos simples
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setYear(dto.getYear());
        book.setPages(dto.getPages());
        book.setLanguage(dto.getLanguage());

        // Publisher
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found with id " + dto.getPublisherId()));
            book.setPublisher(publisher);
        }

        // Category
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id " + dto.getCategoryId()));
            book.setCategory(category);
        }

        // Authors
        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(id -> authorRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Author with ID " + id + " not found")))
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        } else {
            book.setAuthors(new HashSet<>()); // evitar null
        }

        Book saved = bookRepository.save(book);
        return convertToDTO(saved);
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    // ===================== CONVERSIÓN =====================

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getYear(),
                book.getPages(),
                book.getLanguage(),
                book.getPublisher() != null ? book.getPublisher().getId() : null,
                book.getCategory() != null ? book.getCategory().getId() : null,
                book.getAuthors() != null
                        ? book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet())
                        : null
        );
    }
}
