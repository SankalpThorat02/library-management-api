package com.sankalp.library.dto;

import jakarta.validation.constraints.NotNull;

public class BorrowRequest {

    @NotNull
    private Integer memberId;

    @NotNull
    private Integer bookId;

    public BorrowRequest() {

    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
