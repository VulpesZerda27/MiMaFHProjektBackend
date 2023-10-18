package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAllBookAuthors() {
        return authorService.getAllBookAuthors();
    }

    @GetMapping("/{authorId}")
    public Author getBookAuthorById(@PathVariable Long authorId) {
        return authorService.getBookAuthorById(authorId);
    }

    @PostMapping
    public ResponseEntity<Author> createBookAuthor(@RequestBody @Valid Author author) {
        Author createdAuthor = authorService.createBookAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public Author updateBookAuthor(@PathVariable Long authorId, @RequestBody @Valid Author authorDetails) {
        return authorService.updateBookAuthor(authorId, authorDetails);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteBookAuthorById(@PathVariable Long authorId) {
        authorService.deleteBookAuthorById(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
