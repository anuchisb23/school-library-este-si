package com.libraryproject.School.library.graphql;

import com.libraryproject.School.library.dto.PublisherDTO;
import com.libraryproject.School.library.service.PublisherService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PublisherGraphql {

    private final PublisherService publisherService;

    public PublisherGraphql(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @QueryMapping
    public List<PublisherDTO> getAllPublishers() {
        return publisherService.findAll();
    }

    @QueryMapping
    public PublisherDTO getPublisherById(@Argument Long id) {
        PublisherDTO dto = publisherService.findById(id);
        if (dto == null) {
            throw new RuntimeException("Publisher not found with id " + id);
        }
        return dto;
    }

    @MutationMapping
    public PublisherDTO createPublisher(@Argument("publisher") PublisherDTO publisherDTO) {
        return publisherService.save(publisherDTO);
    }

    @MutationMapping
    public PublisherDTO updatePublisher(@Argument Long id, @Argument("publisher") PublisherDTO publisherDTO) {
        PublisherDTO updated = publisherService.update(id, publisherDTO);
        if (updated == null) {
            throw new RuntimeException("Publisher not found with id " + id);
        }
        return updated;
    }

    @MutationMapping
    public Boolean deletePublisher(@Argument Long id) {
        boolean deleted = publisherService.deleteById(id);
        if (!deleted) {
            throw new RuntimeException("Publisher not found with id " + id);
        }
        return true;
    }
}
