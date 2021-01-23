package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.service.DocumentStoreService;
import com.synsoft.xerus.web.rest.errors.BadRequestAlertException;
import com.synsoft.xerus.service.dto.DocumentStoreDTO;
import com.synsoft.xerus.service.dto.DocumentStoreCriteria;
import com.synsoft.xerus.service.DocumentStoreQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.synsoft.xerus.domain.DocumentStore}.
 */
@RestController
@RequestMapping("/api")
public class DocumentStoreResource {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreResource.class);

    private static final String ENTITY_NAME = "documentStore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentStoreService documentStoreService;

    private final DocumentStoreQueryService documentStoreQueryService;

    public DocumentStoreResource(DocumentStoreService documentStoreService, DocumentStoreQueryService documentStoreQueryService) {
        this.documentStoreService = documentStoreService;
        this.documentStoreQueryService = documentStoreQueryService;
    }

    /**
     * {@code POST  /document-stores} : Create a new documentStore.
     *
     * @param documentStoreDTO the documentStoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentStoreDTO, or with status {@code 400 (Bad Request)} if the documentStore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-stores")
    public ResponseEntity<DocumentStoreDTO> createDocumentStore(@RequestBody DocumentStoreDTO documentStoreDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentStore : {}", documentStoreDTO);
        if (documentStoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentStoreDTO result = documentStoreService.save(documentStoreDTO);
        return ResponseEntity.created(new URI("/api/document-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-stores} : Updates an existing documentStore.
     *
     * @param documentStoreDTO the documentStoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStoreDTO,
     * or with status {@code 400 (Bad Request)} if the documentStoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentStoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-stores")
    public ResponseEntity<DocumentStoreDTO> updateDocumentStore(@RequestBody DocumentStoreDTO documentStoreDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentStore : {}", documentStoreDTO);
        if (documentStoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentStoreDTO result = documentStoreService.save(documentStoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentStoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-stores} : get all the documentStores.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentStores in body.
     */
    @GetMapping("/document-stores")
    public ResponseEntity<List<DocumentStoreDTO>> getAllDocumentStores(DocumentStoreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DocumentStores by criteria: {}", criteria);
        Page<DocumentStoreDTO> page = documentStoreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-stores/count} : count all the documentStores.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/document-stores/count")
    public ResponseEntity<Long> countDocumentStores(DocumentStoreCriteria criteria) {
        log.debug("REST request to count DocumentStores by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentStoreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /document-stores/:id} : get the "id" documentStore.
     *
     * @param id the id of the documentStoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentStoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-stores/{id}")
    public ResponseEntity<DocumentStoreDTO> getDocumentStore(@PathVariable Long id) {
        log.debug("REST request to get DocumentStore : {}", id);
        Optional<DocumentStoreDTO> documentStoreDTO = documentStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentStoreDTO);
    }

    /**
     * {@code DELETE  /document-stores/:id} : delete the "id" documentStore.
     *
     * @param id the id of the documentStoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-stores/{id}")
    public ResponseEntity<Void> deleteDocumentStore(@PathVariable Long id) {
        log.debug("REST request to delete DocumentStore : {}", id);
        documentStoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
