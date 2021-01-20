package com.synsoft.xerus.service.impl;

import com.synsoft.xerus.service.DocumentStoreHistoryService;
import com.synsoft.xerus.domain.DocumentStoreHistory;
import com.synsoft.xerus.repository.DocumentStoreHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentStoreHistory}.
 */
@Service
@Transactional
public class DocumentStoreHistoryServiceImpl implements DocumentStoreHistoryService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreHistoryServiceImpl.class);

    private final DocumentStoreHistoryRepository documentStoreHistoryRepository;

    public DocumentStoreHistoryServiceImpl(DocumentStoreHistoryRepository documentStoreHistoryRepository) {
        this.documentStoreHistoryRepository = documentStoreHistoryRepository;
    }

    @Override
    public DocumentStoreHistory save(DocumentStoreHistory documentStoreHistory) {
        log.debug("Request to save DocumentStoreHistory : {}", documentStoreHistory);
        return documentStoreHistoryRepository.save(documentStoreHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentStoreHistory> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStoreHistories");
        return documentStoreHistoryRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentStoreHistory> findOne(Long id) {
        log.debug("Request to get DocumentStoreHistory : {}", id);
        return documentStoreHistoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentStoreHistory : {}", id);
        documentStoreHistoryRepository.deleteById(id);
    }
}
