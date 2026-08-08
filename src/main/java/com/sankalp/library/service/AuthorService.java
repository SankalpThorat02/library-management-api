package com.sankalp.library.service;

import com.sankalp.library.dto.AuthorRequest;
import com.sankalp.library.dto.AuthorResponse;
import com.sankalp.library.entity.Author;
import com.sankalp.library.exception.AuthorNotFoundException;
import com.sankalp.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
}
