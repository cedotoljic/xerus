package com.synsoft.xerus.service;

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
public class DocumentStoreHistoryService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreHistoryService.class);

    private final DocumentStoreHistoryRepository documentStoreHistoryRepository;

    public DocumentStoreHistoryService(DocumentStoreHistoryRepository documentStoreHistoryRepository) {
        this.documentStoreHistoryRepository = documentStoreHistoryRepository;
    }

    /**
     * Save a documentStoreHistory.
     *
     * @param documentStoreHistory the entity to save.
     * @return the persisted entity.
     */
    public DocumentStoreHistory save(DocumentStoreHistory documentStoreHistory) {
        log.debug("Request to save DocumentStoreHistory : {}", documentStoreHistory);
        return documentStoreHistoryRepository.save(documentStoreHistory);
    }

    /**
     * Get all the documentStoreHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentStoreHistory> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStoreHistories");
        return documentStoreHistoryRepository.findAll(pageable);
    }


    /**
     * Get one documentStoreHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStoreHistory> findOne(Long id) {
        log.debug("Request to get DocumentStoreHistory : {}", id);
        return documentStoreHistoryRepository.findById(id);
    }

    /**
     * Delete the documentStoreHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentStoreHistory : {}", id);
        documentStoreHistoryRepository.deleteById(id);
    }
}
