package com.synsoft.xerus.service;

import com.synsoft.xerus.domain.DocumentStoreVersion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DocumentStoreVersion}.
 */
public interface DocumentStoreVersionService {

    /**
     * Save a documentStoreVersion.
     *
     * @param documentStoreVersion the entity to save.
     * @return the persisted entity.
     */
    DocumentStoreVersion save(DocumentStoreVersion documentStoreVersion);

    /**
     * Get all the documentStoreVersions.
     *
     * @return the list of entities.
     */
    List<DocumentStoreVersion> findAll();


    /**
     * Get the "id" documentStoreVersion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DocumentStoreVersion> findOne(Long id);

    /**
     * Delete the "id" documentStoreVersion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
