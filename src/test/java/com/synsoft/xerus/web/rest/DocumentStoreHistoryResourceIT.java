package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.XerusApp;
import com.synsoft.xerus.config.TestSecurityConfiguration;
import com.synsoft.xerus.domain.DocumentStoreHistory;
import com.synsoft.xerus.repository.DocumentStoreHistoryRepository;
import com.synsoft.xerus.service.DocumentStoreHistoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.synsoft.xerus.domain.enumeration.DocumentStatus;
/**
 * Integration tests for the {@link DocumentStoreHistoryResource} REST controller.
 */
@SpringBootTest(classes = { XerusApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DocumentStoreHistoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final byte[] DEFAULT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOC_CONTENT_TYPE = "image/png";

    private static final DocumentStatus DEFAULT_STATUS = DocumentStatus.NEW;
    private static final DocumentStatus UPDATED_STATUS = DocumentStatus.IN_PROGRESS;

    private static final Boolean DEFAULT_IS_FOLDER = false;
    private static final Boolean UPDATED_IS_FOLDER = true;

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE = "BBBBBBBBBB";

    @Autowired
    private DocumentStoreHistoryRepository documentStoreHistoryRepository;

    @Autowired
    private DocumentStoreHistoryService documentStoreHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentStoreHistoryMockMvc;

    private DocumentStoreHistory documentStoreHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStoreHistory createEntity(EntityManager em) {
        DocumentStoreHistory documentStoreHistory = new DocumentStoreHistory()
            .name(DEFAULT_NAME)
            .extension(DEFAULT_EXTENSION)
            .contentType(DEFAULT_CONTENT_TYPE)
            .size(DEFAULT_SIZE)
            .doc(DEFAULT_DOC)
            .docContentType(DEFAULT_DOC_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .isFolder(DEFAULT_IS_FOLDER)
            .owner(DEFAULT_OWNER)
            .responsible(DEFAULT_RESPONSIBLE);
        return documentStoreHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStoreHistory createUpdatedEntity(EntityManager em) {
        DocumentStoreHistory documentStoreHistory = new DocumentStoreHistory()
            .name(UPDATED_NAME)
            .extension(UPDATED_EXTENSION)
            .contentType(UPDATED_CONTENT_TYPE)
            .size(UPDATED_SIZE)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .isFolder(UPDATED_IS_FOLDER)
            .owner(UPDATED_OWNER)
            .responsible(UPDATED_RESPONSIBLE);
        return documentStoreHistory;
    }

    @BeforeEach
    public void initTest() {
        documentStoreHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentStoreHistory() throws Exception {
        int databaseSizeBeforeCreate = documentStoreHistoryRepository.findAll().size();
        // Create the DocumentStoreHistory
        restDocumentStoreHistoryMockMvc.perform(post("/api/document-store-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreHistory)))
            .andExpect(status().isCreated());

        // Validate the DocumentStoreHistory in the database
        List<DocumentStoreHistory> documentStoreHistoryList = documentStoreHistoryRepository.findAll();
        assertThat(documentStoreHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentStoreHistory testDocumentStoreHistory = documentStoreHistoryList.get(documentStoreHistoryList.size() - 1);
        assertThat(testDocumentStoreHistory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentStoreHistory.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testDocumentStoreHistory.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testDocumentStoreHistory.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testDocumentStoreHistory.getDoc()).isEqualTo(DEFAULT_DOC);
        assertThat(testDocumentStoreHistory.getDocContentType()).isEqualTo(DEFAULT_DOC_CONTENT_TYPE);
        assertThat(testDocumentStoreHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDocumentStoreHistory.isIsFolder()).isEqualTo(DEFAULT_IS_FOLDER);
        assertThat(testDocumentStoreHistory.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testDocumentStoreHistory.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void createDocumentStoreHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentStoreHistoryRepository.findAll().size();

        // Create the DocumentStoreHistory with an existing ID
        documentStoreHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStoreHistoryMockMvc.perform(post("/api/document-store-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStoreHistory in the database
        List<DocumentStoreHistory> documentStoreHistoryList = documentStoreHistoryRepository.findAll();
        assertThat(documentStoreHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentStoreHistories() throws Exception {
        // Initialize the database
        documentStoreHistoryRepository.saveAndFlush(documentStoreHistory);

        // Get all the documentStoreHistoryList
        restDocumentStoreHistoryMockMvc.perform(get("/api/document-store-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStoreHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].docContentType").value(hasItem(DEFAULT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].doc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isFolder").value(hasItem(DEFAULT_IS_FOLDER.booleanValue())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE)));
    }
    
    @Test
    @Transactional
    public void getDocumentStoreHistory() throws Exception {
        // Initialize the database
        documentStoreHistoryRepository.saveAndFlush(documentStoreHistory);

        // Get the documentStoreHistory
        restDocumentStoreHistoryMockMvc.perform(get("/api/document-store-histories/{id}", documentStoreHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentStoreHistory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.docContentType").value(DEFAULT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.doc").value(Base64Utils.encodeToString(DEFAULT_DOC)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isFolder").value(DEFAULT_IS_FOLDER.booleanValue()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE));
    }
    @Test
    @Transactional
    public void getNonExistingDocumentStoreHistory() throws Exception {
        // Get the documentStoreHistory
        restDocumentStoreHistoryMockMvc.perform(get("/api/document-store-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentStoreHistory() throws Exception {
        // Initialize the database
        documentStoreHistoryService.save(documentStoreHistory);

        int databaseSizeBeforeUpdate = documentStoreHistoryRepository.findAll().size();

        // Update the documentStoreHistory
        DocumentStoreHistory updatedDocumentStoreHistory = documentStoreHistoryRepository.findById(documentStoreHistory.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentStoreHistory are not directly saved in db
        em.detach(updatedDocumentStoreHistory);
        updatedDocumentStoreHistory
            .name(UPDATED_NAME)
            .extension(UPDATED_EXTENSION)
            .contentType(UPDATED_CONTENT_TYPE)
            .size(UPDATED_SIZE)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .isFolder(UPDATED_IS_FOLDER)
            .owner(UPDATED_OWNER)
            .responsible(UPDATED_RESPONSIBLE);

        restDocumentStoreHistoryMockMvc.perform(put("/api/document-store-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentStoreHistory)))
            .andExpect(status().isOk());

        // Validate the DocumentStoreHistory in the database
        List<DocumentStoreHistory> documentStoreHistoryList = documentStoreHistoryRepository.findAll();
        assertThat(documentStoreHistoryList).hasSize(databaseSizeBeforeUpdate);
        DocumentStoreHistory testDocumentStoreHistory = documentStoreHistoryList.get(documentStoreHistoryList.size() - 1);
        assertThat(testDocumentStoreHistory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentStoreHistory.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testDocumentStoreHistory.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testDocumentStoreHistory.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testDocumentStoreHistory.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testDocumentStoreHistory.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testDocumentStoreHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumentStoreHistory.isIsFolder()).isEqualTo(UPDATED_IS_FOLDER);
        assertThat(testDocumentStoreHistory.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testDocumentStoreHistory.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentStoreHistory() throws Exception {
        int databaseSizeBeforeUpdate = documentStoreHistoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStoreHistoryMockMvc.perform(put("/api/document-store-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreHistory)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStoreHistory in the database
        List<DocumentStoreHistory> documentStoreHistoryList = documentStoreHistoryRepository.findAll();
        assertThat(documentStoreHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentStoreHistory() throws Exception {
        // Initialize the database
        documentStoreHistoryService.save(documentStoreHistory);

        int databaseSizeBeforeDelete = documentStoreHistoryRepository.findAll().size();

        // Delete the documentStoreHistory
        restDocumentStoreHistoryMockMvc.perform(delete("/api/document-store-histories/{id}", documentStoreHistory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentStoreHistory> documentStoreHistoryList = documentStoreHistoryRepository.findAll();
        assertThat(documentStoreHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}