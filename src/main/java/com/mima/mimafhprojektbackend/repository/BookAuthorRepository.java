package com.mima.mimafhprojektbackend.repository;
import com.mima.mimafhprojektbackend.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<Author, Long> {
}
