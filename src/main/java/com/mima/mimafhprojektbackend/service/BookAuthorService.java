package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.repository.BookAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }
    public List<Author> getAllBookAuthors() {
        return bookAuthorRepository.findAll();
    }

    public Optional<Author> getBookAuthorById(Long bookAuthorId) {
        return bookAuthorRepository.findById(bookAuthorId);
    }
}
