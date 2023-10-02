package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.Author;
import com.mima.mimafhprojektbackend.service.BookAuthorService;
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
    public List<Author> getAllBookAuthors() {
        return bookAuthorService.getAllBookAuthors();
    }

    @GetMapping("/{bookAuthorId}")
    public Optional<Author> getBookAuthorById(@PathVariable Long bookAuthorId) {
        return bookAuthorService.getBookAuthorById(bookAuthorId);
    }
}
