package com.workshop.bookshelf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workshop.bookshelf.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    
    // Spring Data derives the SQL from the method name automatically
    // SELECT * FROM books WHERE genre = ?

    List<Book> findByGenre(String genre);

    // SELECT * FROM books WHERE LOWER(author) LIKE LOWER(CONCAT('%', ?, '%'))
    List<Book> findByAuthorContainingIgnoreCase(String author);

}
