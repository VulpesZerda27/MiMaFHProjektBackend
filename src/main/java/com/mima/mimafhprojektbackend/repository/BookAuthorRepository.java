package com.mima.mimafhprojektbackend.repository;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<Author, Long> {
    public Optional<Author> getAuthorByFirstNameAndLastName(String firstName, String lastName);
}
