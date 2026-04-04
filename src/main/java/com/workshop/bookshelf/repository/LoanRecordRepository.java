package com.workshop.bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workshop.bookshelf.model.LoanRecord;

@Repository
public interface LoanRecordRepository extends JpaRepository<LoanRecord, Long>{
    
}
