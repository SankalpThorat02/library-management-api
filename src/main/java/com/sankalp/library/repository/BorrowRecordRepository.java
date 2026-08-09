package com.sankalp.library.repository;

import com.sankalp.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {

    boolean existsByBookIdAndReturnDateIsNull(Integer bookId);
}
