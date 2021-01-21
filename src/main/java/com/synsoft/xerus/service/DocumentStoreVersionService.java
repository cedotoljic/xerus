package com.synsoft.xerus.service;

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
public class DocumentStoreVersionService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreVersionService.class);

    private final DocumentStoreVersionRepository documentStoreVersionRepository;

    public DocumentStoreVersionService(DocumentStoreVersionRepository documentStoreVersionRepository) {
        this.documentStoreVersionRepository = documentStoreVersionRepository;
    }

    /**
     * Save a documentStoreVersion.
     *
     * @param documentStoreVersion the entity to save.
     * @return the persisted entity.
     */
    public DocumentStoreVersion save(DocumentStoreVersion documentStoreVersion) {
        log.debug("Request to save DocumentStoreVersion : {}", documentStoreVersion);
        return documentStoreVersionRepository.save(documentStoreVersion);
    }

    /**
     * Get all the documentStoreVersions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentStoreVersion> findAll() {
        log.debug("Request to get all DocumentStoreVersions");
        return documentStoreVersionRepository.findAll();
    }


    /**
     * Get one documentStoreVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStoreVersion> findOne(Long id) {
        log.debug("Request to get DocumentStoreVersion : {}", id);
        return documentStoreVersionRepository.findById(id);
    }

    /**
     * Delete the documentStoreVersion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentStoreVersion : {}", id);
        documentStoreVersionRepository.deleteById(id);
    }
}
