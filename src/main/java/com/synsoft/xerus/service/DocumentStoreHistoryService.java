package com.synsoft.xerus.service;

import com.synsoft.xerus.domain.DocumentStoreHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DocumentStoreHistory}.
 */
public interface DocumentStoreHistoryService {

    /**
     * Save a documentStoreHistory.
     *
     * @param documentStoreHistory the entity to save.
     * @return the persisted entity.
     */
    DocumentStoreHistory save(DocumentStoreHistory documentStoreHistory);

    /**
     * Get all the documentStoreHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentStoreHistory> findAll(Pageable pageable);


    /**
     * Get the "id" documentStoreHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentStoreHistory> findOne(Long id);

    /**
     * Delete the "id" documentStoreHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
