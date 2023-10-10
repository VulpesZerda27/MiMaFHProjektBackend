package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.repository.BookAuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;
    public List<Author> getAllBookAuthors() {
        return bookAuthorRepository.findAll();
    }

    public Author getBookAuthorById(Long bookAuthorId) {
        return bookAuthorRepository.findById(bookAuthorId).orElseThrow();
    }
}
