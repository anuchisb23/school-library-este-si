package com.libraryproject.School.library.repository;

import com.libraryproject.School.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);  
    boolean existsByUsername(String username);    
}
   


