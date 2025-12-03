package com.libraryproject.School.library.controller;

import com.libraryproject.School.library.dto.BookDTO;
import com.libraryproject.School.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Books", description = "Controller for managing books in the library")
@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //  Crear libro
    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO saved = bookService.save(bookDTO);
        // 201 Created con Location
        return ResponseEntity
                .created(URI.create("/api/books/" + saved.getId()))
                .body(saved);
    }

    //  Obtener todos los libros (sin paginación)
    @Operation(summary = "Get all books (no pagination)")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    //  Obtener todos los libros con paginación
    @Operation(summary = "Get all books with pagination")
    @GetMapping("/paginated")
    public ResponseEntity<Page<BookDTO>> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    //  Obtener libro por ID
    @Operation(summary = "Get a book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        Optional<BookDTO> book = bookService.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    //  Actualizar libro
    @Operation(summary = "Update an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO) {
        Optional<BookDTO> existing = bookService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookDTO.setId(id);
        BookDTO updated = bookService.save(bookDTO);
        return ResponseEntity.ok(updated);
    }

    //  Eliminar libro
    @Operation(summary = "Delete a book by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<BookDTO> existing = bookService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
