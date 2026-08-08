package com.sankalp.library.service;

import com.sankalp.library.dto.AuthorRequest;
import com.sankalp.library.dto.AuthorResponse;
import com.sankalp.library.entity.Author;
import com.sankalp.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

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
}
