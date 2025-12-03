package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    // Crear o actualizar libro
    BookDTO save(BookDTO bookDTO);

    // Obtener todos los libros
    List<BookDTO> findAll();

    // Obtener todos los libros con paginación
    Page<BookDTO> findAll(Pageable pageable);

    // Buscar por ID
    Optional<BookDTO> findById(Long id);

    // Eliminar por ID
    void deleteById(Long id);
}
