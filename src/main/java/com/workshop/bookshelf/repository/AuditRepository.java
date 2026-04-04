package com.workshop.bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workshop.bookshelf.model.AuditEntry;

public interface AuditRepository extends JpaRepository<AuditEntry, Long>{
    
}
