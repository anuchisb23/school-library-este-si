package com.libraryproject.School.library.controller;

import com.libraryproject.School.library.dto.PublisherDTO;
import com.libraryproject.School.library.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Publishers", description = "Controller for managing publishers")
@RestController
@RequestMapping("/api/publishers")
@CrossOrigin(origins = "*")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    //  Crear editorial
    @Operation(summary = "Create a new publisher")
    @PostMapping
    public ResponseEntity<PublisherDTO> create(@Valid @RequestBody PublisherDTO dto) {
        PublisherDTO saved = publisherService.save(dto);
        // Devuelve 201 Created con Location
        return ResponseEntity
                .created(URI.create("/api/publishers/" + saved.getId()))
                .body(saved);
    }

    //  Obtener todas las editoriales
    @Operation(summary = "Get all publishers")
    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getAll() {
        List<PublisherDTO> publishers = publisherService.findAll();
        return ResponseEntity.ok(publishers);
    }

    //  Obtener por ID
    @Operation(summary = "Get a publisher by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getById(@PathVariable Long id) {
        PublisherDTO dto = publisherService.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    //  Actualizar editorial
    @Operation(summary = "Update an existing publisher")
    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> update(@PathVariable Long id, @Valid @RequestBody PublisherDTO dto) {
        PublisherDTO updated = publisherService.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    //  Eliminar editorial
    @Operation(summary = "Delete a publisher by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = publisherService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
