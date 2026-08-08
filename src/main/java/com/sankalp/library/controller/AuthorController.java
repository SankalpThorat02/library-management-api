package com.sankalp.library.controller;

import com.sankalp.library.dto.AuthorRequest;
import com.sankalp.library.dto.AuthorResponse;
import com.sankalp.library.service.AuthorService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
