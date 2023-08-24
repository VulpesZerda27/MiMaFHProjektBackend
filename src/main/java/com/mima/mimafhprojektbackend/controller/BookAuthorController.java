package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.BookAuthor;
import com.mima.mimafhprojektbackend.service.BookAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookAuthor")
public class BookAuthorController {
    private final BookAuthorService bookAuthorService;

    @GetMapping
    public List<BookAuthor> getAllBookAuthors() {
        return bookAuthorService.getAllBookAuthors();
    }

    @GetMapping("/{bookAuthorId}")
    public Optional<BookAuthor> getBookAuthorById(@PathVariable Long bookAuthorId) {
        return bookAuthorService.getBookAuthorById(bookAuthorId);
    }

    @PostMapping
    public BookAuthor createCategory(@RequestBody @Valid BookAuthor bookAuthor) {
        return bookAuthorService.createBookAuthor(bookAuthor);
    }

    @PutMapping("/{bookAuthorId}")
    public BookAuthor updateBookAuthor(@PathVariable Long bookAuthorId, @RequestBody @Valid BookAuthor bookAuthorDetails) {
        return bookAuthorService.updateBookAuthor(bookAuthorId, bookAuthorDetails);
    }

    @DeleteMapping("/{bookAuthorId}")
    public void deleteBookAuthorById(@PathVariable Long bookAuthorId) {
        bookAuthorService.deleteBookAuthorById(bookAuthorId);
    }
}
