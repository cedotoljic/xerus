package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.XerusApp;
import com.synsoft.xerus.config.TestSecurityConfiguration;
import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.repository.DocumentStoreRepository;
import com.synsoft.xerus.service.DocumentStoreService;

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
 * Integration tests for the {@link DocumentStoreResource} REST controller.
 */
@SpringBootTest(classes = { XerusApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DocumentStoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final Long DEFAULT_SIZE = 1L;
    private static final Long UPDATED_SIZE = 2L;

    private static final byte[] DEFAULT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOC_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_THUMBNAIL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_THUMBNAIL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_THUMBNAIL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_THUMBNAIL_CONTENT_TYPE = "image/png";

    private static final DocumentStatus DEFAULT_STATUS = DocumentStatus.NEW;
    private static final DocumentStatus UPDATED_STATUS = DocumentStatus.IN_PROGRESS;

    private static final Boolean DEFAULT_IS_FOLDER = false;
    private static final Boolean UPDATED_IS_FOLDER = true;

    private static final Boolean DEFAULT_VERSION_CONTROLLED = false;
    private static final Boolean UPDATED_VERSION_CONTROLLED = true;

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE = "BBBBBBBBBB";

    @Autowired
    private DocumentStoreRepository documentStoreRepository;

    @Autowired
    private DocumentStoreService documentStoreService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentStoreMockMvc;

    private DocumentStore documentStore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStore createEntity(EntityManager em) {
        DocumentStore documentStore = new DocumentStore()
            .name(DEFAULT_NAME)
            .extension(DEFAULT_EXTENSION)
            .size(DEFAULT_SIZE)
            .doc(DEFAULT_DOC)
            .docContentType(DEFAULT_DOC_CONTENT_TYPE)
            .thumbnail(DEFAULT_THUMBNAIL)
            .thumbnailContentType(DEFAULT_THUMBNAIL_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .isFolder(DEFAULT_IS_FOLDER)
            .versionControlled(DEFAULT_VERSION_CONTROLLED)
            .version(DEFAULT_VERSION)
            .owner(DEFAULT_OWNER)
            .responsible(DEFAULT_RESPONSIBLE);
        return documentStore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStore createUpdatedEntity(EntityManager em) {
        DocumentStore documentStore = new DocumentStore()
            .name(UPDATED_NAME)
            .extension(UPDATED_EXTENSION)
            .size(UPDATED_SIZE)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .thumbnail(UPDATED_THUMBNAIL)
            .thumbnailContentType(UPDATED_THUMBNAIL_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .isFolder(UPDATED_IS_FOLDER)
            .versionControlled(UPDATED_VERSION_CONTROLLED)
            .version(UPDATED_VERSION)
            .owner(UPDATED_OWNER)
            .responsible(UPDATED_RESPONSIBLE);
        return documentStore;
    }

    @BeforeEach
    public void initTest() {
        documentStore = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentStore() throws Exception {
        int databaseSizeBeforeCreate = documentStoreRepository.findAll().size();
        // Create the DocumentStore
        restDocumentStoreMockMvc.perform(post("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStore)))
            .andExpect(status().isCreated());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentStore testDocumentStore = documentStoreList.get(documentStoreList.size() - 1);
        assertThat(testDocumentStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentStore.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testDocumentStore.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testDocumentStore.getDoc()).isEqualTo(DEFAULT_DOC);
        assertThat(testDocumentStore.getDocContentType()).isEqualTo(DEFAULT_DOC_CONTENT_TYPE);
        assertThat(testDocumentStore.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testDocumentStore.getThumbnailContentType()).isEqualTo(DEFAULT_THUMBNAIL_CONTENT_TYPE);
        assertThat(testDocumentStore.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDocumentStore.isIsFolder()).isEqualTo(DEFAULT_IS_FOLDER);
        assertThat(testDocumentStore.isVersionControlled()).isEqualTo(DEFAULT_VERSION_CONTROLLED);
        assertThat(testDocumentStore.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDocumentStore.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testDocumentStore.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void createDocumentStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentStoreRepository.findAll().size();

        // Create the DocumentStore with an existing ID
        documentStore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStoreMockMvc.perform(post("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStore)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentStores() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList
        restDocumentStoreMockMvc.perform(get("/api/document-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].docContentType").value(hasItem(DEFAULT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].doc").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOC))))
            .andExpect(jsonPath("$.[*].thumbnailContentType").value(hasItem(DEFAULT_THUMBNAIL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(Base64Utils.encodeToString(DEFAULT_THUMBNAIL))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isFolder").value(hasItem(DEFAULT_IS_FOLDER.booleanValue())))
            .andExpect(jsonPath("$.[*].versionControlled").value(hasItem(DEFAULT_VERSION_CONTROLLED.booleanValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE)));
    }
    
    @Test
    @Transactional
    public void getDocumentStore() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get the documentStore
        restDocumentStoreMockMvc.perform(get("/api/document-stores/{id}", documentStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentStore.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.intValue()))
            .andExpect(jsonPath("$.docContentType").value(DEFAULT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.doc").value(Base64Utils.encodeToString(DEFAULT_DOC)))
            .andExpect(jsonPath("$.thumbnailContentType").value(DEFAULT_THUMBNAIL_CONTENT_TYPE))
            .andExpect(jsonPath("$.thumbnail").value(Base64Utils.encodeToString(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isFolder").value(DEFAULT_IS_FOLDER.booleanValue()))
            .andExpect(jsonPath("$.versionControlled").value(DEFAULT_VERSION_CONTROLLED.booleanValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE));
    }
    @Test
    @Transactional
    public void getNonExistingDocumentStore() throws Exception {
        // Get the documentStore
        restDocumentStoreMockMvc.perform(get("/api/document-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentStore() throws Exception {
        // Initialize the database
        documentStoreService.save(documentStore);

        int databaseSizeBeforeUpdate = documentStoreRepository.findAll().size();

        // Update the documentStore
        DocumentStore updatedDocumentStore = documentStoreRepository.findById(documentStore.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentStore are not directly saved in db
        em.detach(updatedDocumentStore);
        updatedDocumentStore
            .name(UPDATED_NAME)
            .extension(UPDATED_EXTENSION)
            .size(UPDATED_SIZE)
            .doc(UPDATED_DOC)
            .docContentType(UPDATED_DOC_CONTENT_TYPE)
            .thumbnail(UPDATED_THUMBNAIL)
            .thumbnailContentType(UPDATED_THUMBNAIL_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .isFolder(UPDATED_IS_FOLDER)
            .versionControlled(UPDATED_VERSION_CONTROLLED)
            .version(UPDATED_VERSION)
            .owner(UPDATED_OWNER)
            .responsible(UPDATED_RESPONSIBLE);

        restDocumentStoreMockMvc.perform(put("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentStore)))
            .andExpect(status().isOk());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeUpdate);
        DocumentStore testDocumentStore = documentStoreList.get(documentStoreList.size() - 1);
        assertThat(testDocumentStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentStore.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testDocumentStore.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testDocumentStore.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testDocumentStore.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testDocumentStore.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testDocumentStore.getThumbnailContentType()).isEqualTo(UPDATED_THUMBNAIL_CONTENT_TYPE);
        assertThat(testDocumentStore.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumentStore.isIsFolder()).isEqualTo(UPDATED_IS_FOLDER);
        assertThat(testDocumentStore.isVersionControlled()).isEqualTo(UPDATED_VERSION_CONTROLLED);
        assertThat(testDocumentStore.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDocumentStore.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testDocumentStore.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentStore() throws Exception {
        int databaseSizeBeforeUpdate = documentStoreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStoreMockMvc.perform(put("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStore)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentStore() throws Exception {
        // Initialize the database
        documentStoreService.save(documentStore);

        int databaseSizeBeforeDelete = documentStoreRepository.findAll().size();

        // Delete the documentStore
        restDocumentStoreMockMvc.perform(delete("/api/document-stores/{id}", documentStore.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
