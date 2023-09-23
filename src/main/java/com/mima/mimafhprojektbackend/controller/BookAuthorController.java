package com.mima.mimafhprojektbackend.controller;

import com.mima.mimafhprojektbackend.model.BookAuthor;
import com.mima.mimafhprojektbackend.service.BookAuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
}
