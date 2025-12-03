package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.PublisherDTO;
import com.libraryproject.School.library.model.Book;
import com.libraryproject.School.library.model.Publisher;
import com.libraryproject.School.library.repository.BookRepository;
import com.libraryproject.School.library.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository; 

    public PublisherServiceImpl(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    // Crear o actualizar Publisher desde DTO
    @Override
    public PublisherDTO save(PublisherDTO dto) {
        Publisher publisher = convertToEntity(dto);
        Publisher saved = publisherRepository.save(publisher);
        return convertToDTO(saved);
    }

    // Obtener todos como DTO
    @Override
    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener por ID como DTO
    @Override
    public PublisherDTO findById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        return publisher.map(this::convertToDTO).orElse(null);
    }

    // Actualizar por ID con DTO
    @Override
    public PublisherDTO update(Long id, PublisherDTO dto) {
        Optional<Publisher> existing = publisherRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }
        Publisher publisher = convertToEntity(dto);
        publisher.setId(id);
        Publisher updated = publisherRepository.save(publisher);
        return convertToDTO(updated);
    }

    // Eliminar por ID
    @Override
    public boolean deleteById(Long id) {
        Optional<Publisher> existing = publisherRepository.findById(id);
        if (existing.isPresent()) {
            publisherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // ===================== Métodos de conversión =====================

    private PublisherDTO convertToDTO(Publisher publisher) {
        List<Long> bookIds = publisher.getBooks()
                .stream()
                .map(Book::getId)
                .collect(Collectors.toList());

        return new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getAddress(),
                publisher.getPhone(),
                publisher.getEmail(),
                bookIds
        );
    }

    private Publisher convertToEntity(PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setId(dto.getId());
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setPhone(dto.getPhone());
        publisher.setEmail(dto.getEmail());

        // Relacionar los libros por IDs si vienen en el DTO
        if (dto.getBookIds() != null && !dto.getBookIds().isEmpty()) {
            List<Book> books = bookRepository.findAllById(dto.getBookIds());
            publisher.setBooks(books);
        }

        return publisher;
    }
}
