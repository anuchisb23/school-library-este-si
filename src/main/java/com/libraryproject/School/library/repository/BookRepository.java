package com.libraryproject.School.library.repository;

import com.libraryproject.School.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // ===================== CONSULTAS DERIVADAS =====================

    // Buscar por ISBN (único)
    Optional<Book> findByIsbn(String isbn);

    // Buscar por título (sin paginación)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Buscar por título (con paginación)
    Page<Book> findByTitleContainingIgnoreCase(String title, @NonNull Pageable pageable);

    // Buscar por idioma (sin paginación)
    List<Book> findByLanguageIgnoreCase(String language);

    // Buscar por idioma (con paginación)
    Page<Book> findByLanguageIgnoreCase(String language, @NonNull Pageable pageable);

    // Buscar por año de publicación
    List<Book> findByYear(int year);

    // Soporte de paginación general
    @NonNull
    Page<Book> findAll(@NonNull Pageable pageable);

    // ===================== CONSULTAS PERSONALIZADAS =====================

    // 1️ Buscar libros publicados entre dos años
    @Query("SELECT b FROM Book b WHERE b.year BETWEEN :startYear AND :endYear")
    List<Book> findBooksBetweenYears(@Param("startYear") int startYear, @Param("endYear") int endYear);

    // 2️ Buscar libros por apellido del autor
    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE LOWER(a.lastName) = LOWER(:lastName)")
    List<Book> findBooksByAuthorLastName(@Param("lastName") String lastName);

    // 3️ Buscar libros por editorial (Publisher) por nombre
    @Query("SELECT b FROM Book b WHERE LOWER(b.publisher.name) LIKE LOWER(CONCAT('%', :publisherName, '%'))")
    List<Book> findBooksByPublisherName(@Param("publisherName") String publisherName);

    // 4️ Buscar libros por categoría
    @Query("SELECT b FROM Book b WHERE LOWER(b.category.name) LIKE LOWER(CONCAT('%', :categoryName, '%'))")
    List<Book> findBooksByCategoryName(@Param("categoryName") String categoryName);

    // 5️ Contar libros por idioma
    @Query("SELECT COUNT(b) FROM Book b WHERE LOWER(b.language) = LOWER(:language)")
    long countBooksByLanguage(@Param("language") String language);
}
