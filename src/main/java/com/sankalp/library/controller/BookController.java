package com.sankalp.library.controller;

import com.sankalp.library.dto.BookRequest;
import com.sankalp.library.dto.BookResponse;
import com.sankalp.library.entity.Book;
import com.sankalp.library.repository.BookRepository;
import com.sankalp.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService =  bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody @Valid BookRequest request) {
        BookResponse response = bookService.addBook(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
