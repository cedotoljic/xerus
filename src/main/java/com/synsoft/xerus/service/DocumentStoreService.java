package com.synsoft.xerus.service;

import com.synsoft.xerus.domain.DocumentStore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DocumentStore}.
 */
public interface DocumentStoreService {

    /**
     * Save a documentStore.
     *
     * @param documentStore the entity to save.
     * @return the persisted entity.
     */
    DocumentStore save(DocumentStore documentStore);

    /**
     * Get all the documentStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DocumentStore> findAll(Pageable pageable);


    /**
     * Get the "id" documentStore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentStore> findOne(Long id);

    /**
     * Delete the "id" documentStore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
