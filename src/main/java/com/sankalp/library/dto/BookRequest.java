package com.sankalp.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    @NotNull
    private Integer publishedYear;

    @NotNull
    private Integer authorId;

    public BookRequest() {

    }

    public BookRequest(String title, String isbn, Integer publishedYear, Integer authorId) {
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}
