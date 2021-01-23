package com.synsoft.xerus.service;

import com.synsoft.xerus.domain.DocumentStoreHistory;
import com.synsoft.xerus.repository.DocumentStoreHistoryRepository;
import com.synsoft.xerus.service.dto.DocumentStoreHistoryDTO;
import com.synsoft.xerus.service.mapper.DocumentStoreHistoryMapper;
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

    private final DocumentStoreHistoryMapper documentStoreHistoryMapper;

    public DocumentStoreHistoryService(DocumentStoreHistoryRepository documentStoreHistoryRepository, DocumentStoreHistoryMapper documentStoreHistoryMapper) {
        this.documentStoreHistoryRepository = documentStoreHistoryRepository;
        this.documentStoreHistoryMapper = documentStoreHistoryMapper;
    }

    /**
     * Save a documentStoreHistory.
     *
     * @param documentStoreHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentStoreHistoryDTO save(DocumentStoreHistoryDTO documentStoreHistoryDTO) {
        log.debug("Request to save DocumentStoreHistory : {}", documentStoreHistoryDTO);
        DocumentStoreHistory documentStoreHistory = documentStoreHistoryMapper.toEntity(documentStoreHistoryDTO);
        documentStoreHistory = documentStoreHistoryRepository.save(documentStoreHistory);
        return documentStoreHistoryMapper.toDto(documentStoreHistory);
    }

    /**
     * Get all the documentStoreHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentStoreHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentStoreHistories");
        return documentStoreHistoryRepository.findAll(pageable)
            .map(documentStoreHistoryMapper::toDto);
    }


    /**
     * Get one documentStoreHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStoreHistoryDTO> findOne(Long id) {
        log.debug("Request to get DocumentStoreHistory : {}", id);
        return documentStoreHistoryRepository.findById(id)
            .map(documentStoreHistoryMapper::toDto);
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
