package com.mima.mimafhprojektbackend.repository;
import com.mima.mimafhprojektbackend.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName);
}
