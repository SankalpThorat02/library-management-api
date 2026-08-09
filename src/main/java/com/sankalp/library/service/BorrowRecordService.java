package com.sankalp.library.service;

import com.sankalp.library.dto.BorrowRequest;
import com.sankalp.library.dto.BorrowResponse;
import com.sankalp.library.entity.Book;
import com.sankalp.library.entity.BorrowRecord;
import com.sankalp.library.entity.Member;
import com.sankalp.library.exception.BookNotAvailableException;
import com.sankalp.library.exception.BookNotBorrowedException;
import com.sankalp.library.exception.BookNotFoundException;
import com.sankalp.library.exception.MemberNotFoundException;
import com.sankalp.library.repository.BookRepository;
import com.sankalp.library.repository.BorrowRecordRepository;
import com.sankalp.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, MemberRepository memberRepository, BookRepository bookRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() ->
                        new MemberNotFoundException("Member with ID: " + request.getMemberId() + " not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException("Book with ID: " +  request.getBookId() + " not found"));


        boolean isBookNotReturned = borrowRecordRepository.existsByBookIdAndReturnDateIsNull(book.getId());

        if(isBookNotReturned) {
            throw new BookNotAvailableException("Book with ID: " + book.getId() + " - " + book.getTitle() + " currently not available");
        }

        BorrowRecord record = borrowRecordRepository.save(new BorrowRecord(
                member,
                book,
                LocalDate.now(),
                LocalDate.now().plusMonths(1))
        );

        return new BorrowResponse(
                record.getId(),
                record.getBook().getId(),
                record.getMember().getId(),
                record.getBorrowDate(),
                record.getDueDate(),
                null
        );
    }

    @Transactional
    public BorrowResponse returnBook(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID: " + bookId + " not found"));

        BorrowRecord record = borrowRecordRepository.findByBookIdAndReturnDateIsNull(book.getId())
                .orElseThrow(() -> new BookNotBorrowedException("Book with ID: " + book.getId() + " not borrowed. Cant Return"));

        record.setReturnDate(LocalDate.now());

        record = borrowRecordRepository.save(record);

        return new  BorrowResponse(
                record.getId(),
                record.getBook().getId(),
                record.getMember().getId(),
                record.getBorrowDate(),
                record.getDueDate(),
                record.getReturnDate()
        );
    }
}
