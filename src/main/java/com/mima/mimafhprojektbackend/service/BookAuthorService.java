package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.BookAuthor;
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
    public List<BookAuthor> getAllBookAuthors() {
        return bookAuthorRepository.findAll();
    }

    public Optional<BookAuthor> getBookAuthorById(Long bookAuthorId) {
        return bookAuthorRepository.findById(bookAuthorId);
    }

    public BookAuthor createBookAuthor(BookAuthor bookAuthor) {
        return bookAuthorRepository.save(bookAuthor);
    }

    public BookAuthor updateBookAuthor(Long bookAuthorId, BookAuthor bookAuthorDetails) {
        BookAuthor bookAuthor = bookAuthorRepository.findById(bookAuthorId).orElseThrow();
       bookAuthor.setAuthorFirstName(bookAuthorDetails.getAuthorFirstName());
       bookAuthor.setAuthorLastName(bookAuthorDetails.getAuthorLastName());

        return bookAuthorRepository.save(bookAuthor);
    }

    public void deleteBookAuthorById(Long bookAuthorId) {
        bookAuthorRepository.deleteById(bookAuthorId);
    }

}
