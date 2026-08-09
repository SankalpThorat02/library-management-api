package com.sankalp.library.controller;

import com.sankalp.library.dto.BorrowRequest;
import com.sankalp.library.dto.BorrowResponse;
import com.sankalp.library.service.BorrowRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowResponse> borrowBook(@RequestBody @Valid BorrowRequest request) {
        BorrowResponse response = borrowRecordService.borrowBook(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
