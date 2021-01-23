package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.service.DocumentStoreVersionService;
import com.synsoft.xerus.web.rest.errors.BadRequestAlertException;
import com.synsoft.xerus.service.dto.DocumentStoreVersionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.synsoft.xerus.domain.DocumentStoreVersion}.
 */
@RestController
@RequestMapping("/api")
public class DocumentStoreVersionResource {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreVersionResource.class);

    private static final String ENTITY_NAME = "documentStoreVersion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentStoreVersionService documentStoreVersionService;

    public DocumentStoreVersionResource(DocumentStoreVersionService documentStoreVersionService) {
        this.documentStoreVersionService = documentStoreVersionService;
    }

    /**
     * {@code POST  /document-store-versions} : Create a new documentStoreVersion.
     *
     * @param documentStoreVersionDTO the documentStoreVersionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentStoreVersionDTO, or with status {@code 400 (Bad Request)} if the documentStoreVersion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-store-versions")
    public ResponseEntity<DocumentStoreVersionDTO> createDocumentStoreVersion(@RequestBody DocumentStoreVersionDTO documentStoreVersionDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentStoreVersion : {}", documentStoreVersionDTO);
        if (documentStoreVersionDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentStoreVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentStoreVersionDTO result = documentStoreVersionService.save(documentStoreVersionDTO);
        return ResponseEntity.created(new URI("/api/document-store-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-store-versions} : Updates an existing documentStoreVersion.
     *
     * @param documentStoreVersionDTO the documentStoreVersionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStoreVersionDTO,
     * or with status {@code 400 (Bad Request)} if the documentStoreVersionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentStoreVersionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-store-versions")
    public ResponseEntity<DocumentStoreVersionDTO> updateDocumentStoreVersion(@RequestBody DocumentStoreVersionDTO documentStoreVersionDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentStoreVersion : {}", documentStoreVersionDTO);
        if (documentStoreVersionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentStoreVersionDTO result = documentStoreVersionService.save(documentStoreVersionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentStoreVersionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-store-versions} : get all the documentStoreVersions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentStoreVersions in body.
     */
    @GetMapping("/document-store-versions")
    public List<DocumentStoreVersionDTO> getAllDocumentStoreVersions() {
        log.debug("REST request to get all DocumentStoreVersions");
        return documentStoreVersionService.findAll();
    }

    /**
     * {@code GET  /document-store-versions/:id} : get the "id" documentStoreVersion.
     *
     * @param id the id of the documentStoreVersionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentStoreVersionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-store-versions/{id}")
    public ResponseEntity<DocumentStoreVersionDTO> getDocumentStoreVersion(@PathVariable Long id) {
        log.debug("REST request to get DocumentStoreVersion : {}", id);
        Optional<DocumentStoreVersionDTO> documentStoreVersionDTO = documentStoreVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentStoreVersionDTO);
    }

    /**
     * {@code DELETE  /document-store-versions/:id} : delete the "id" documentStoreVersion.
     *
     * @param id the id of the documentStoreVersionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-store-versions/{id}")
    public ResponseEntity<Void> deleteDocumentStoreVersion(@PathVariable Long id) {
        log.debug("REST request to delete DocumentStoreVersion : {}", id);
        documentStoreVersionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
