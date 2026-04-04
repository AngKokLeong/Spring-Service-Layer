package com.workshop.bookshelf.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.bookshelf.model.Book;
import com.workshop.bookshelf.model.LoanRecord;
import com.workshop.bookshelf.repository.BookRepository;
import com.workshop.bookshelf.repository.LoanRecordRepository;



@Service
public class LoanService {
    
    private final AuditService auditService;
    private final LoanRecordRepository loanRecordRepository;
    private final BookRepository bookRepository;
    
    public LoanService(LoanRecordRepository loanRecordRepository, BookRepository bookRepository, AuditService auditService){
        this.loanRecordRepository = loanRecordRepository;
        this.bookRepository = bookRepository;
        this.auditService = auditService;
    }


    @Transactional
    public List<LoanRecord> findActiveLoans(){
        return null;
    }

    @Transactional
    public LoanRecord borrowBook(Long bookId, String borrowerName){
        Book book = bookRepository.findById(bookId)
                                .orElseThrow(
                                        () -> new IllegalArgumentException("Book not found: " + bookId)
                                );
        
        boolean alreadyOnLoan = loanRecordRepository.existsByBookAndReturnedFalse(book);
        
        if (alreadyOnLoan) {
            throw new IllegalStateException("Book '" + book.getTitle() + "' is already on loan.");
        }

        LoanRecord record = new LoanRecord(book, borrowerName);
        LoanRecord saved = loanRecordRepository.save(record);

  
        // Audit in a SEPARATE transaction so it is never rolled back
        // even if borrowBook itself fails later in the same call stack.
        auditService.logAction("BORROW", bookId, borrowerName);

        return saved;
    }

    @Transactional
    public LoanRecord returnBook(Long loanId){
        LoanRecord record = loanRecordRepository.findById(loanId)
                                                .orElseThrow(
                                                    () -> new IllegalArgumentException("Loan not found :" + loanId)
                                                );

        if (record.isReturned()){
            throw new IllegalStateException("Loan not found: " + loanId);
        }

        record.setReturned(true);
        record.setReturnDate(LocalDate.now());

        LoanRecord saved = loanRecordRepository.save(record);

        
        auditService.logAction("RETURN", record.getBook().getId(), record.getBorrowerName());

        return saved;

    }

}
