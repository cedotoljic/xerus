package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.domain.DocumentStoreHistory;
import com.synsoft.xerus.service.DocumentStoreHistoryService;
import com.synsoft.xerus.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.synsoft.xerus.domain.DocumentStoreHistory}.
 */
@RestController
@RequestMapping("/api")
public class DocumentStoreHistoryResource {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreHistoryResource.class);

    private static final String ENTITY_NAME = "documentStoreHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentStoreHistoryService documentStoreHistoryService;

    public DocumentStoreHistoryResource(DocumentStoreHistoryService documentStoreHistoryService) {
        this.documentStoreHistoryService = documentStoreHistoryService;
    }

    /**
     * {@code POST  /document-store-histories} : Create a new documentStoreHistory.
     *
     * @param documentStoreHistory the documentStoreHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentStoreHistory, or with status {@code 400 (Bad Request)} if the documentStoreHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-store-histories")
    public ResponseEntity<DocumentStoreHistory> createDocumentStoreHistory(@RequestBody DocumentStoreHistory documentStoreHistory) throws URISyntaxException {
        log.debug("REST request to save DocumentStoreHistory : {}", documentStoreHistory);
        if (documentStoreHistory.getId() != null) {
            throw new BadRequestAlertException("A new documentStoreHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentStoreHistory result = documentStoreHistoryService.save(documentStoreHistory);
        return ResponseEntity.created(new URI("/api/document-store-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-store-histories} : Updates an existing documentStoreHistory.
     *
     * @param documentStoreHistory the documentStoreHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStoreHistory,
     * or with status {@code 400 (Bad Request)} if the documentStoreHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentStoreHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-store-histories")
    public ResponseEntity<DocumentStoreHistory> updateDocumentStoreHistory(@RequestBody DocumentStoreHistory documentStoreHistory) throws URISyntaxException {
        log.debug("REST request to update DocumentStoreHistory : {}", documentStoreHistory);
        if (documentStoreHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentStoreHistory result = documentStoreHistoryService.save(documentStoreHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentStoreHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-store-histories} : get all the documentStoreHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentStoreHistories in body.
     */
    @GetMapping("/document-store-histories")
    public ResponseEntity<List<DocumentStoreHistory>> getAllDocumentStoreHistories(Pageable pageable) {
        log.debug("REST request to get a page of DocumentStoreHistories");
        Page<DocumentStoreHistory> page = documentStoreHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-store-histories/:id} : get the "id" documentStoreHistory.
     *
     * @param id the id of the documentStoreHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentStoreHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-store-histories/{id}")
    public ResponseEntity<DocumentStoreHistory> getDocumentStoreHistory(@PathVariable Long id) {
        log.debug("REST request to get DocumentStoreHistory : {}", id);
        Optional<DocumentStoreHistory> documentStoreHistory = documentStoreHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentStoreHistory);
    }

    /**
     * {@code DELETE  /document-store-histories/:id} : delete the "id" documentStoreHistory.
     *
     * @param id the id of the documentStoreHistory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-store-histories/{id}")
    public ResponseEntity<Void> deleteDocumentStoreHistory(@PathVariable Long id) {
        log.debug("REST request to delete DocumentStoreHistory : {}", id);
        documentStoreHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
