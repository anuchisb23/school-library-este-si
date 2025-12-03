package com.libraryproject.School.library.controller;

import com.libraryproject.School.library.dto.AuthorDTO;
import com.libraryproject.School.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Authors", description = "Controller for managing authors")
@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //  Crear un autor
    @Operation(summary = "Create a new author")
    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO saved = authorService.save(authorDTO);
        // 201 Created + Location en header
        return ResponseEntity
                .created(URI.create("/api/authors/" + saved.getId()))
                .body(saved);
    }

    //  Obtener todos los autores
    @Operation(summary = "Get all authors")
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<AuthorDTO> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    //  Obtener autor por ID
    @Operation(summary = "Get an author by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable Long id) {
        return authorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //  Actualizar autor
    @Operation(summary = "Update an existing author")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        Optional<AuthorDTO> existing = authorService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorDTO.setId(id);
        AuthorDTO updated = authorService.save(authorDTO);
        return ResponseEntity.ok(updated);
    }

    //  Eliminar autor
    @Operation(summary = "Delete an author by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<AuthorDTO> existing = authorService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
