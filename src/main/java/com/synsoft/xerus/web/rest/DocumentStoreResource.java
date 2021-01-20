package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.service.DocumentStoreService;
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

    public DocumentStoreResource(DocumentStoreService documentStoreService) {
        this.documentStoreService = documentStoreService;
    }

    /**
     * {@code POST  /document-stores} : Create a new documentStore.
     *
     * @param documentStore the documentStore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentStore, or with status {@code 400 (Bad Request)} if the documentStore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-stores")
    public ResponseEntity<DocumentStore> createDocumentStore(@RequestBody DocumentStore documentStore) throws URISyntaxException {
        log.debug("REST request to save DocumentStore : {}", documentStore);
        if (documentStore.getId() != null) {
            throw new BadRequestAlertException("A new documentStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentStore result = documentStoreService.save(documentStore);
        return ResponseEntity.created(new URI("/api/document-stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-stores} : Updates an existing documentStore.
     *
     * @param documentStore the documentStore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentStore,
     * or with status {@code 400 (Bad Request)} if the documentStore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentStore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-stores")
    public ResponseEntity<DocumentStore> updateDocumentStore(@RequestBody DocumentStore documentStore) throws URISyntaxException {
        log.debug("REST request to update DocumentStore : {}", documentStore);
        if (documentStore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentStore result = documentStoreService.save(documentStore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentStore.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-stores} : get all the documentStores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentStores in body.
     */
    @GetMapping("/document-stores")
    public ResponseEntity<List<DocumentStore>> getAllDocumentStores(Pageable pageable) {
        log.debug("REST request to get a page of DocumentStores");
        Page<DocumentStore> page = documentStoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-stores/:id} : get the "id" documentStore.
     *
     * @param id the id of the documentStore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentStore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-stores/{id}")
    public ResponseEntity<DocumentStore> getDocumentStore(@PathVariable Long id) {
        log.debug("REST request to get DocumentStore : {}", id);
        Optional<DocumentStore> documentStore = documentStoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentStore);
    }

    /**
     * {@code DELETE  /document-stores/:id} : delete the "id" documentStore.
     *
     * @param id the id of the documentStore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-stores/{id}")
    public ResponseEntity<Void> deleteDocumentStore(@PathVariable Long id) {
        log.debug("REST request to delete DocumentStore : {}", id);
        documentStoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
