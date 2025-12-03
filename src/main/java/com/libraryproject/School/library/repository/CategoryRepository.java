package com.libraryproject.School.library.repository;

import com.libraryproject.School.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // ===================== CONSULTAS PERSONALIZADAS =====================

    // 1️ Buscar categorías que tengan libros asociados
    @Query("SELECT DISTINCT c FROM Category c JOIN c.books b")
    List<Category> findCategoriesWithBooks();

    // 2️ Buscar categorías sin libros asociados
    @Query("SELECT c FROM Category c WHERE c.books IS EMPTY")
    List<Category> findCategoriesWithoutBooks();

    // 3️ Contar categorías con más de X libros
    @Query("SELECT c FROM Category c WHERE SIZE(c.books) > :minBooks")
    List<Category> findCategoriesWithMoreThan(@Param("minBooks") int minBooks);

    // 4️  Buscar categorías por palabra clave en la descripción
    @Query("SELECT c FROM Category c WHERE LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> searchByDescription(@Param("keyword") String keyword);

    // 5️ Contar cuántas categorías existen por nombre (para validar duplicados)
    @Query("SELECT COUNT(c) FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
    long countByCategoryName(@Param("name") String name);
}
