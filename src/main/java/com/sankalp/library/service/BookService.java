package com.sankalp.library.service;

import com.sankalp.library.dto.BookRequest;
import com.sankalp.library.dto.BookResponse;
import com.sankalp.library.entity.Author;
import com.sankalp.library.entity.Book;
import com.sankalp.library.exception.AuthorNotFoundException;
import com.sankalp.library.exception.BookNotFoundException;
import com.sankalp.library.repository.AuthorRepository;
import com.sankalp.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public BookResponse addBook(BookRequest request) {

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() ->
                        new AuthorNotFoundException("Book Author Not Found")
                );

        Book book = new Book(
                request.getTitle(),
                request.getIsbn(),
                request.getPublishedYear(),
                author
        );

        Book savedBook =  bookRepository.save(book);

        return new BookResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getIsbn(),
                savedBook.getAuthor().getName(),
                savedBook.getAuthor().getId(),
                savedBook.getPublishedYear()
        );

    }

    public BookResponse getBookById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new BookNotFoundException("Book with ID: " + id + " not found"));

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor().getName(),
                book.getAuthor().getId(),
                book.getPublishedYear()
        );
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
               .map(book -> new BookResponse(
                       book.getId(),
                       book.getTitle(),
                       book.getIsbn(),
                       book.getAuthor().getName(),
                       book.getAuthor().getId(),
                       book.getPublishedYear()
               )).collect(Collectors.toList());

    }

    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }
}
