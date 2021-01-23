package com.synsoft.xerus.service;

import com.synsoft.xerus.domain.DocumentStoreVersion;
import com.synsoft.xerus.repository.DocumentStoreVersionRepository;
import com.synsoft.xerus.service.dto.DocumentStoreVersionDTO;
import com.synsoft.xerus.service.mapper.DocumentStoreVersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DocumentStoreVersion}.
 */
@Service
@Transactional
public class DocumentStoreVersionService {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreVersionService.class);

    private final DocumentStoreVersionRepository documentStoreVersionRepository;

    private final DocumentStoreVersionMapper documentStoreVersionMapper;

    public DocumentStoreVersionService(DocumentStoreVersionRepository documentStoreVersionRepository, DocumentStoreVersionMapper documentStoreVersionMapper) {
        this.documentStoreVersionRepository = documentStoreVersionRepository;
        this.documentStoreVersionMapper = documentStoreVersionMapper;
    }

    /**
     * Save a documentStoreVersion.
     *
     * @param documentStoreVersionDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentStoreVersionDTO save(DocumentStoreVersionDTO documentStoreVersionDTO) {
        log.debug("Request to save DocumentStoreVersion : {}", documentStoreVersionDTO);
        DocumentStoreVersion documentStoreVersion = documentStoreVersionMapper.toEntity(documentStoreVersionDTO);
        documentStoreVersion = documentStoreVersionRepository.save(documentStoreVersion);
        return documentStoreVersionMapper.toDto(documentStoreVersion);
    }

    /**
     * Get all the documentStoreVersions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentStoreVersionDTO> findAll() {
        log.debug("Request to get all DocumentStoreVersions");
        return documentStoreVersionRepository.findAll().stream()
            .map(documentStoreVersionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one documentStoreVersion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentStoreVersionDTO> findOne(Long id) {
        log.debug("Request to get DocumentStoreVersion : {}", id);
        return documentStoreVersionRepository.findById(id)
            .map(documentStoreVersionMapper::toDto);
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
