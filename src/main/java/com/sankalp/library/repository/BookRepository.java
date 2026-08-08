package com.sankalp.library.repository;

import com.sankalp.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByAuthorId(Integer id);
}
