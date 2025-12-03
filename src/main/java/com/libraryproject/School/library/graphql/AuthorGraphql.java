package com.libraryproject.School.library.graphql;

import com.libraryproject.School.library.dto.AuthorDTO;
import com.libraryproject.School.library.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorGraphql {

    private final AuthorService authorService;

    public AuthorGraphql(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<AuthorDTO> getAllAuthors() {
        return authorService.findAll();
    }

    @QueryMapping
    public AuthorDTO getAuthorById(@Argument Long id) {
        return authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
    }

    @MutationMapping
    public AuthorDTO createAuthor(@Argument("author") AuthorDTO authorDTO) {
        return authorService.save(authorDTO);
    }

    @MutationMapping
    public AuthorDTO updateAuthor(@Argument Long id, @Argument("author") AuthorDTO authorDTO) {
        authorDTO.setId(id);
        return authorService.save(authorDTO);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id) {
        authorService.deleteById(id);
        return true;
    }
}
