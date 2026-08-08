package com.sankalp.library.dto;

public class BookResponse {

    private Integer id;
    private String title;
    private String isbn;
    private String authorName;
    private Integer authorId;
    private Integer publishedYear;


    public BookResponse(Integer id, String title, String isbn, String authorName, Integer authorId, Integer publishedYear) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.authorName = authorName;
        this.authorId = authorId;
        this.publishedYear = publishedYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }
}
