package com.sankalp.library.controller;

import com.sankalp.library.dto.AuthorRequest;
import com.sankalp.library.dto.AuthorResponse;
import com.sankalp.library.service.AuthorService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public AuthorResponse getAuthorById(@PathVariable Integer id) {

        return authorService.getAuthorById(id);
    }

    @GetMapping
    public List<AuthorResponse> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
