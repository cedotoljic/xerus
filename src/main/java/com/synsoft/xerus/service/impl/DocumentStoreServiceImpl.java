package com.synsoft.xerus.service.impl;

import com.synsoft.xerus.service.DocumentStoreService;
import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.repository.DocumentStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentStore}.
 */
@Service
@Transactional
public class DocumentStoreServiceImpl implements DocumentStoreService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreServiceImpl.class);

    private final DocumentStoreRepository documentStoreRepository;

    public DocumentStoreServiceImpl(DocumentStoreRepository documentStoreRepository) {
        this.documentStoreRepository = documentStoreRepository;
    }

    @Override
    public DocumentStore save(DocumentStore documentStore) {
        log.debug("Request to save DocumentStore : {}", documentStore);
        return documentStoreRepository.save(documentStore);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentStore> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStores");
        return documentStoreRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentStore> findOne(Long id) {
        log.debug("Request to get DocumentStore : {}", id);
        return documentStoreRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentStore : {}", id);
        documentStoreRepository.deleteById(id);
    }
}
