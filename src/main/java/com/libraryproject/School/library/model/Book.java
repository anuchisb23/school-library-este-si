package com.libraryproject.School.library.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int pages;

    @Column(length = 50)
    private String language;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonManagedReference(value = "publisher-books") // ✅ ahora se serializa
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonManagedReference(value = "category-books") // ✅ ahora se serializa
    private Category category;

    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @JsonManagedReference(value = "book-authors") // ✅ ya lo tenías correcto
    private Set<Author> authors = new HashSet<>();

    // Constructores
    public Book() {}

    public Book(String title, String isbn, int year, int pages, String language,
                Publisher publisher, Category category) {
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.pages = pages;
        this.language = language;
        this.publisher = publisher;
        this.category = category;
    }

    // Getters y Setters (tradicionales)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Author> getAuthors() {
        return authors;
    }
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    // Métodos auxiliares para ManyToMany
    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    // equals & hashCode (basado en id)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString (sin relaciones para evitar recursión infinita)
    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", isbn='" + isbn + '\'' +
            ", year=" + year +
            ", pages=" + pages +
            ", language='" + language + '\'' +
            '}';
    }
}
