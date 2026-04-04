package com.workshop.bookshelf.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AuditEntry")
@Data
public class AuditEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "bookId", nullable = false)
    private Long bookId;

    @Column(name = "actor", nullable = false)
    private String actor;   

    @Column(name = "auditEntryDateTime", nullable = false)
    private LocalDateTime auditEntryDateTime;

    public AuditEntry() {}

    public AuditEntry(String action, Long bookId, String actor, LocalDateTime auditEntryDateTime){
        this.action = action;
        this.bookId = bookId;
        this.actor = actor;
        this.auditEntryDateTime = auditEntryDateTime;
    }
}
