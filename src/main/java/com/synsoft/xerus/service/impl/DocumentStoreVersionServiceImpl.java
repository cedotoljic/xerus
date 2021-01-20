package com.synsoft.xerus.service.impl;

import com.synsoft.xerus.service.DocumentStoreVersionService;
import com.synsoft.xerus.domain.DocumentStoreVersion;
import com.synsoft.xerus.repository.DocumentStoreVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentStoreVersion}.
 */
@Service
@Transactional
public class DocumentStoreVersionServiceImpl implements DocumentStoreVersionService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreVersionServiceImpl.class);

    private final DocumentStoreVersionRepository documentStoreVersionRepository;

    public DocumentStoreVersionServiceImpl(DocumentStoreVersionRepository documentStoreVersionRepository) {
        this.documentStoreVersionRepository = documentStoreVersionRepository;
    }

    @Override
    public DocumentStoreVersion save(DocumentStoreVersion documentStoreVersion) {
        log.debug("Request to save DocumentStoreVersion : {}", documentStoreVersion);
        return documentStoreVersionRepository.save(documentStoreVersion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentStoreVersion> findAll() {
        log.debug("Request to get all DocumentStoreVersions");
        return documentStoreVersionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentStoreVersion> findOne(Long id) {
        log.debug("Request to get DocumentStoreVersion : {}", id);
        return documentStoreVersionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentStoreVersion : {}", id);
        documentStoreVersionRepository.deleteById(id);
    }
}
