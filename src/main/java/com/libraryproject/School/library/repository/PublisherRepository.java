package com.libraryproject.School.library.repository;

import com.libraryproject.School.library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    // Consultas derivadas
    Optional<Publisher> findByNameIgnoreCase(String name);
    Optional<Publisher> findByEmailIgnoreCase(String email);
    Optional<Publisher> findByPhone(String phone);
    List<Publisher> findByNameContainingIgnoreCase(String name);

    // ===================== CONSULTAS PERSONALIZADAS =====================

    // 1️ Buscar editoriales que tengan libros asociados
    @Query("SELECT DISTINCT p FROM Publisher p JOIN p.books b")
    List<Publisher> findPublishersWithBooks();

    // 2️ Buscar editoriales sin libros asociados
    @Query("SELECT p FROM Publisher p WHERE p.books IS EMPTY")
    List<Publisher> findPublishersWithoutBooks();

    // 3️ Buscar editoriales por palabra clave en dirección
    @Query("SELECT p FROM Publisher p WHERE LOWER(p.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Publisher> searchByAddress(@Param("keyword") String keyword);

    // 4️ Buscar editoriales que tengan más de X libros publicados
    @Query("SELECT p FROM Publisher p WHERE SIZE(p.books) > :minBooks")
    List<Publisher> findPublishersWithMoreThan(@Param("minBooks") int minBooks);

    // 5️ Contar cuántos libros tiene cada editorial
    @Query("SELECT p.name, COUNT(b) FROM Publisher p LEFT JOIN p.books b GROUP BY p.name")
    List<Object[]> countBooksByPublisher();
}
