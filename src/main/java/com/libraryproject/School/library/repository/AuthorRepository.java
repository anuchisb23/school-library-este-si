package com.libraryproject.School.library.repository;

import com.libraryproject.School.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // 1 Consulta personalizada: Buscar autores por nombre o apellido (JPQL)
    @Query("SELECT a FROM Author a WHERE LOWER(a.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(a.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Author> searchByName(@Param("name") String name);

    // 2 Consulta personalizada: Buscar por nombre completo exacto
    @Query("SELECT a FROM Author a WHERE LOWER(a.firstName) = LOWER(:firstName) AND LOWER(a.lastName) = LOWER(:lastName)")
    Optional<Author> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // 3 Consulta personalizada: Buscar autores nacidos después de cierta fecha
    @Query("SELECT a FROM Author a WHERE a.birthDate > :date")
    List<Author> findAuthorsBornAfter(@Param("date") LocalDate date);

    // 4 Consulta personalizada: Contar autores por nacionalidad
    @Query("SELECT COUNT(a) FROM Author a WHERE LOWER(a.nationality) = LOWER(:nationality)")
    long countByNationality(@Param("nationality") String nationality);

    // 5 Consulta personalizada: Buscar autores que tengan libros registrados
    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b")
    List<Author> findAuthorsWithBooks();
}
