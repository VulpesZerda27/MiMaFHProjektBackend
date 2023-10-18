package com.mima.mimafhprojektbackend.service;
import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public List<Author> getAllBookAuthors() {
        return authorRepository.findAll();
    }

    public Author getBookAuthorById(Long bookAuthorId) {
        return authorRepository.findById(bookAuthorId).orElseThrow();
    }

    public Author createBookAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateBookAuthor(Long bookAuthorId, Author authorDetails) {
        Author author = authorRepository.findById(bookAuthorId).orElseThrow();
        author.setFirstName(authorDetails.getFirstName());
        author.setLastName(authorDetails.getLastName());

        return authorRepository.save(author);
    }

    public void deleteBookAuthorById(Long bookAuthorId) {
        authorRepository.findById(bookAuthorId).orElseThrow();
        authorRepository.deleteById(bookAuthorId);
    }
}
