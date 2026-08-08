package com.sankalp.library.service;

import com.sankalp.library.dto.AuthorRequest;
import com.sankalp.library.dto.AuthorResponse;
import com.sankalp.library.entity.Author;
import com.sankalp.library.exception.AuthorHasBooksException;
import com.sankalp.library.exception.AuthorNotFoundException;
import com.sankalp.library.repository.AuthorRepository;
import com.sankalp.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public AuthorResponse createAuthor(AuthorRequest request) {

        Author author = new Author(request.getName(), request.getNationality());

        Author createdAuthor = authorRepository.save(author);

        return new AuthorResponse(
                createdAuthor.getId(),
                createdAuthor.getName(),
                createdAuthor.getNationality()
        );
    }

    public AuthorResponse getAuthorById(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author with ID: " + id + " not found")
                );

        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getNationality()
        );
    }

    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        List<AuthorResponse> responses = new ArrayList<>();

        for (Author author : authors) {
            AuthorResponse authorResponse = new AuthorResponse(
                    author.getId(),
                    author.getName(),
                    author.getNationality()
            );

            responses.add(authorResponse);
        }

        return responses;
    }

    public void deleteAuthorById(int id) {

        Author author = authorRepository.findById(id)
                        .orElseThrow(() ->
                                new AuthorNotFoundException("Author with ID: " + id + " not found")
                        );

        boolean booksExists = bookRepository.existsByAuthorId(id);

        if(booksExists) {
            throw new AuthorHasBooksException("Author with ID: " + id + " has books");
        }
        else {
            authorRepository.delete(author);
        }
    }
}
