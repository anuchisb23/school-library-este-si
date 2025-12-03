package com.libraryproject.School.library.graphql;

import com.libraryproject.School.library.dto.BookDTO;
import com.libraryproject.School.library.service.BookService;
import com.libraryproject.School.library.dto.PaginatedBooksDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookGraphql {

    private final BookService bookService;

    public BookGraphql(BookService bookService) {
        this.bookService = bookService;
    }
    @QueryMapping
    public List<BookDTO> getAllBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public PaginatedBooksDTO getAllBooksPaginated(@Argument int page, @Argument int size) {
        Page<BookDTO> result = bookService.findAll(PageRequest.of(page, size));
        return new PaginatedBooksDTO(
            result.getContent(),
            result.getTotalElements(),
            result.getTotalPages(),
            result.getNumber(),
            result.getSize()
        );
    }

    @QueryMapping
    public BookDTO getBookById(@Argument Long id) {
        return bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }


    @MutationMapping
    public BookDTO createBook(@Argument("book") BookDTO bookDTO) {
        return bookService.save(bookDTO);
    }

    @MutationMapping
    public BookDTO updateBook(@Argument Long id, @Argument("book") BookDTO bookDTO) {
        bookDTO.setId(id);
        return bookService.save(bookDTO);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.deleteById(id);
        return true;
    }
}
