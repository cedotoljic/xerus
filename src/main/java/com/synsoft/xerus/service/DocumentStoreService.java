package com.synsoft.xerus.service;

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
public class DocumentStoreService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreService.class);

    private final DocumentStoreRepository documentStoreRepository;

    public DocumentStoreService(DocumentStoreRepository documentStoreRepository) {
        this.documentStoreRepository = documentStoreRepository;
    }

    /**
     * Save a documentStore.
     *
     * @param documentStore the entity to save.
     * @return the persisted entity.
     */
    public DocumentStore save(DocumentStore documentStore) {
        log.debug("Request to save DocumentStore : {}", documentStore);
        return documentStoreRepository.save(documentStore);
    }

    /**
     * Get all the documentStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentStore> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStores");
        return documentStoreRepository.findAll(pageable);
    }


    /**
     * Get one documentStore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStore> findOne(Long id) {
        log.debug("Request to get DocumentStore : {}", id);
        return documentStoreRepository.findById(id);
    }

    /**
     * Delete the documentStore by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentStore : {}", id);
        documentStoreRepository.deleteById(id);
    }
}
