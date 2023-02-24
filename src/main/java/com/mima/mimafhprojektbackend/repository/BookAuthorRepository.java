package com.mima.mimafhprojektbackend.repository;
import com.mima.mimafhprojektbackend.model.BookAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
}
