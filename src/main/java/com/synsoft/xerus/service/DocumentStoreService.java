package com.synsoft.xerus.service;

import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.repository.DocumentStoreRepository;
import com.synsoft.xerus.service.dto.DocumentStoreDTO;
import com.synsoft.xerus.service.mapper.DocumentStoreMapper;
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

    private final DocumentStoreMapper documentStoreMapper;

    public DocumentStoreService(DocumentStoreRepository documentStoreRepository, DocumentStoreMapper documentStoreMapper) {
        this.documentStoreRepository = documentStoreRepository;
        this.documentStoreMapper = documentStoreMapper;
    }

    /**
     * Save a documentStore.
     *
     * @param documentStoreDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentStoreDTO save(DocumentStoreDTO documentStoreDTO) {
        log.debug("Request to save DocumentStore : {}", documentStoreDTO);
        DocumentStore documentStore = documentStoreMapper.toEntity(documentStoreDTO);
        documentStore = documentStoreRepository.save(documentStore);
        return documentStoreMapper.toDto(documentStore);
    }

    /**
     * Get all the documentStores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentStoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStores");
        return documentStoreRepository.findAll(pageable)
            .map(documentStoreMapper::toDto);
    }


    /**
     * Get one documentStore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStoreDTO> findOne(Long id) {
        log.debug("Request to get DocumentStore : {}", id);
        return documentStoreRepository.findById(id)
            .map(documentStoreMapper::toDto);
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
