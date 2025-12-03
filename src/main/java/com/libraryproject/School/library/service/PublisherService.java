package com.libraryproject.School.library.service;

import com.libraryproject.School.library.dto.PublisherDTO;
import java.util.List;

public interface PublisherService {
    PublisherDTO save(PublisherDTO dto);
    List<PublisherDTO> findAll();
    PublisherDTO findById(Long id);
    PublisherDTO update(Long id, PublisherDTO dto);
    boolean deleteById(Long id);
}
