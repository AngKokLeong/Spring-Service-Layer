package com.workshop.bookshelf.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.bookshelf.model.AuditEntry;
import com.workshop.bookshelf.repository.AuditRepository;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository){
        this.auditRepository = auditRepository;
    }

    // REQUIRES_NEW: this audit entry is committed immediately and independently
     // It will not be rolled back if the caller rolls back.
    @Transactional
    public void logAction(String action, Long bookId, String actor){
        AuditEntry entry = new AuditEntry(action, bookId, actor, LocalDateTime.now());
        auditRepository.save(entry);
    }

}
