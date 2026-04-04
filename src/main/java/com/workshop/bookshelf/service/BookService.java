package com.workshop.bookshelf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.bookshelf.model.Book;
import com.workshop.bookshelf.repository.BookRepository;



@Service //Register as a Spring-managed bean
public class BookService {
    
    private final BookRepository bookRepository;

    // Constructor injection - Spring wires BookRepository automatically
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    // Read-only @Transactional (readOnly=true) is an optimization hint
    // It tells the JPA provider not to track entity state for dirty checking
    @Transactional(readOnly = true)
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findByGenre(String genre){
        return bookRepository.findByGenre(genre);
    }

    // Write Operation: Default @Transactional uses Progation.REQUIRED
    // A new transaction starts if none exists, existing one is joined
    @Transactional
    public Book save(Book book){
        return bookRepository.save(book);
    }
    
    @Transactional
    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }


}
