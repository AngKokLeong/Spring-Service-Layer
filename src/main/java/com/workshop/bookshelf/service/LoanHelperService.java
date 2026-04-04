package com.workshop.bookshelf.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanHelperService{

    private AuditService auditService;

    public LoanHelperService(AuditService auditService){
        this.auditService = auditService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void helper(){
        auditService.logAction("TEST_PROXY_BYPASS_BUG", Long.valueOf("23"), "MEME");
    }

}