package com.workshop.bookshelf.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "LoanRecord")
@Data
public class LoanRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private String borrowerName;

    
    private LocalDate loanDate;
    private LocalDate returnDate;

    private boolean returned = false;

    public LoanRecord(){}

    public LoanRecord(Book book, String borrowerName){
        this.book = book;
        this.borrowerName = borrowerName;
        this.loanDate = LocalDate.now();
    }



}
