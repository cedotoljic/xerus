package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.XerusApp;
import com.synsoft.xerus.config.TestSecurityConfiguration;
import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.repository.DocumentStoreRepository;
import com.synsoft.xerus.service.DocumentStoreService;
import com.synsoft.xerus.service.dto.DocumentStoreDTO;
import com.synsoft.xerus.service.mapper.DocumentStoreMapper;
import com.synsoft.xerus.service.dto.DocumentStoreCriteria;
import com.synsoft.xerus.service.DocumentStoreQueryService;

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
    private static final Long SMALLER_SIZE = 1L - 1L;

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
    private static final Long SMALLER_VERSION = 1L - 1L;

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE = "BBBBBBBBBB";

    @Autowired
    private DocumentStoreRepository documentStoreRepository;

    @Autowired
    private DocumentStoreMapper documentStoreMapper;

    @Autowired
    private DocumentStoreService documentStoreService;

    @Autowired
    private DocumentStoreQueryService documentStoreQueryService;

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
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(documentStore);
        restDocumentStoreMockMvc.perform(post("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
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
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(documentStore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStoreMockMvc.perform(post("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
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
    public void getDocumentStoresByIdFiltering() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        Long id = documentStore.getId();

        defaultDocumentStoreShouldBeFound("id.equals=" + id);
        defaultDocumentStoreShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentStoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentStoreShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentStoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentStoreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where name equals to DEFAULT_NAME
        defaultDocumentStoreShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the documentStoreList where name equals to UPDATED_NAME
        defaultDocumentStoreShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where name not equals to DEFAULT_NAME
        defaultDocumentStoreShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the documentStoreList where name not equals to UPDATED_NAME
        defaultDocumentStoreShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDocumentStoreShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the documentStoreList where name equals to UPDATED_NAME
        defaultDocumentStoreShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where name is not null
        defaultDocumentStoreShouldBeFound("name.specified=true");

        // Get all the documentStoreList where name is null
        defaultDocumentStoreShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentStoresByNameContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where name contains DEFAULT_NAME
        defaultDocumentStoreShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the documentStoreList where name contains UPDATED_NAME
        defaultDocumentStoreShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where name does not contain DEFAULT_NAME
        defaultDocumentStoreShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the documentStoreList where name does not contain UPDATED_NAME
        defaultDocumentStoreShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresByExtensionIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where extension equals to DEFAULT_EXTENSION
        defaultDocumentStoreShouldBeFound("extension.equals=" + DEFAULT_EXTENSION);

        // Get all the documentStoreList where extension equals to UPDATED_EXTENSION
        defaultDocumentStoreShouldNotBeFound("extension.equals=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByExtensionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where extension not equals to DEFAULT_EXTENSION
        defaultDocumentStoreShouldNotBeFound("extension.notEquals=" + DEFAULT_EXTENSION);

        // Get all the documentStoreList where extension not equals to UPDATED_EXTENSION
        defaultDocumentStoreShouldBeFound("extension.notEquals=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByExtensionIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where extension in DEFAULT_EXTENSION or UPDATED_EXTENSION
        defaultDocumentStoreShouldBeFound("extension.in=" + DEFAULT_EXTENSION + "," + UPDATED_EXTENSION);

        // Get all the documentStoreList where extension equals to UPDATED_EXTENSION
        defaultDocumentStoreShouldNotBeFound("extension.in=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByExtensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where extension is not null
        defaultDocumentStoreShouldBeFound("extension.specified=true");

        // Get all the documentStoreList where extension is null
        defaultDocumentStoreShouldNotBeFound("extension.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentStoresByExtensionContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where extension contains DEFAULT_EXTENSION
        defaultDocumentStoreShouldBeFound("extension.contains=" + DEFAULT_EXTENSION);

        // Get all the documentStoreList where extension contains UPDATED_EXTENSION
        defaultDocumentStoreShouldNotBeFound("extension.contains=" + UPDATED_EXTENSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByExtensionNotContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where extension does not contain DEFAULT_EXTENSION
        defaultDocumentStoreShouldNotBeFound("extension.doesNotContain=" + DEFAULT_EXTENSION);

        // Get all the documentStoreList where extension does not contain UPDATED_EXTENSION
        defaultDocumentStoreShouldBeFound("extension.doesNotContain=" + UPDATED_EXTENSION);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size equals to DEFAULT_SIZE
        defaultDocumentStoreShouldBeFound("size.equals=" + DEFAULT_SIZE);

        // Get all the documentStoreList where size equals to UPDATED_SIZE
        defaultDocumentStoreShouldNotBeFound("size.equals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size not equals to DEFAULT_SIZE
        defaultDocumentStoreShouldNotBeFound("size.notEquals=" + DEFAULT_SIZE);

        // Get all the documentStoreList where size not equals to UPDATED_SIZE
        defaultDocumentStoreShouldBeFound("size.notEquals=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size in DEFAULT_SIZE or UPDATED_SIZE
        defaultDocumentStoreShouldBeFound("size.in=" + DEFAULT_SIZE + "," + UPDATED_SIZE);

        // Get all the documentStoreList where size equals to UPDATED_SIZE
        defaultDocumentStoreShouldNotBeFound("size.in=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size is not null
        defaultDocumentStoreShouldBeFound("size.specified=true");

        // Get all the documentStoreList where size is null
        defaultDocumentStoreShouldNotBeFound("size.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size is greater than or equal to DEFAULT_SIZE
        defaultDocumentStoreShouldBeFound("size.greaterThanOrEqual=" + DEFAULT_SIZE);

        // Get all the documentStoreList where size is greater than or equal to UPDATED_SIZE
        defaultDocumentStoreShouldNotBeFound("size.greaterThanOrEqual=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size is less than or equal to DEFAULT_SIZE
        defaultDocumentStoreShouldBeFound("size.lessThanOrEqual=" + DEFAULT_SIZE);

        // Get all the documentStoreList where size is less than or equal to SMALLER_SIZE
        defaultDocumentStoreShouldNotBeFound("size.lessThanOrEqual=" + SMALLER_SIZE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsLessThanSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size is less than DEFAULT_SIZE
        defaultDocumentStoreShouldNotBeFound("size.lessThan=" + DEFAULT_SIZE);

        // Get all the documentStoreList where size is less than UPDATED_SIZE
        defaultDocumentStoreShouldBeFound("size.lessThan=" + UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresBySizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where size is greater than DEFAULT_SIZE
        defaultDocumentStoreShouldNotBeFound("size.greaterThan=" + DEFAULT_SIZE);

        // Get all the documentStoreList where size is greater than SMALLER_SIZE
        defaultDocumentStoreShouldBeFound("size.greaterThan=" + SMALLER_SIZE);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where status equals to DEFAULT_STATUS
        defaultDocumentStoreShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the documentStoreList where status equals to UPDATED_STATUS
        defaultDocumentStoreShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where status not equals to DEFAULT_STATUS
        defaultDocumentStoreShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the documentStoreList where status not equals to UPDATED_STATUS
        defaultDocumentStoreShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDocumentStoreShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the documentStoreList where status equals to UPDATED_STATUS
        defaultDocumentStoreShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where status is not null
        defaultDocumentStoreShouldBeFound("status.specified=true");

        // Get all the documentStoreList where status is null
        defaultDocumentStoreShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByIsFolderIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where isFolder equals to DEFAULT_IS_FOLDER
        defaultDocumentStoreShouldBeFound("isFolder.equals=" + DEFAULT_IS_FOLDER);

        // Get all the documentStoreList where isFolder equals to UPDATED_IS_FOLDER
        defaultDocumentStoreShouldNotBeFound("isFolder.equals=" + UPDATED_IS_FOLDER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByIsFolderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where isFolder not equals to DEFAULT_IS_FOLDER
        defaultDocumentStoreShouldNotBeFound("isFolder.notEquals=" + DEFAULT_IS_FOLDER);

        // Get all the documentStoreList where isFolder not equals to UPDATED_IS_FOLDER
        defaultDocumentStoreShouldBeFound("isFolder.notEquals=" + UPDATED_IS_FOLDER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByIsFolderIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where isFolder in DEFAULT_IS_FOLDER or UPDATED_IS_FOLDER
        defaultDocumentStoreShouldBeFound("isFolder.in=" + DEFAULT_IS_FOLDER + "," + UPDATED_IS_FOLDER);

        // Get all the documentStoreList where isFolder equals to UPDATED_IS_FOLDER
        defaultDocumentStoreShouldNotBeFound("isFolder.in=" + UPDATED_IS_FOLDER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByIsFolderIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where isFolder is not null
        defaultDocumentStoreShouldBeFound("isFolder.specified=true");

        // Get all the documentStoreList where isFolder is null
        defaultDocumentStoreShouldNotBeFound("isFolder.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionControlledIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where versionControlled equals to DEFAULT_VERSION_CONTROLLED
        defaultDocumentStoreShouldBeFound("versionControlled.equals=" + DEFAULT_VERSION_CONTROLLED);

        // Get all the documentStoreList where versionControlled equals to UPDATED_VERSION_CONTROLLED
        defaultDocumentStoreShouldNotBeFound("versionControlled.equals=" + UPDATED_VERSION_CONTROLLED);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionControlledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where versionControlled not equals to DEFAULT_VERSION_CONTROLLED
        defaultDocumentStoreShouldNotBeFound("versionControlled.notEquals=" + DEFAULT_VERSION_CONTROLLED);

        // Get all the documentStoreList where versionControlled not equals to UPDATED_VERSION_CONTROLLED
        defaultDocumentStoreShouldBeFound("versionControlled.notEquals=" + UPDATED_VERSION_CONTROLLED);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionControlledIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where versionControlled in DEFAULT_VERSION_CONTROLLED or UPDATED_VERSION_CONTROLLED
        defaultDocumentStoreShouldBeFound("versionControlled.in=" + DEFAULT_VERSION_CONTROLLED + "," + UPDATED_VERSION_CONTROLLED);

        // Get all the documentStoreList where versionControlled equals to UPDATED_VERSION_CONTROLLED
        defaultDocumentStoreShouldNotBeFound("versionControlled.in=" + UPDATED_VERSION_CONTROLLED);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionControlledIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where versionControlled is not null
        defaultDocumentStoreShouldBeFound("versionControlled.specified=true");

        // Get all the documentStoreList where versionControlled is null
        defaultDocumentStoreShouldNotBeFound("versionControlled.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version equals to DEFAULT_VERSION
        defaultDocumentStoreShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the documentStoreList where version equals to UPDATED_VERSION
        defaultDocumentStoreShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version not equals to DEFAULT_VERSION
        defaultDocumentStoreShouldNotBeFound("version.notEquals=" + DEFAULT_VERSION);

        // Get all the documentStoreList where version not equals to UPDATED_VERSION
        defaultDocumentStoreShouldBeFound("version.notEquals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultDocumentStoreShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the documentStoreList where version equals to UPDATED_VERSION
        defaultDocumentStoreShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version is not null
        defaultDocumentStoreShouldBeFound("version.specified=true");

        // Get all the documentStoreList where version is null
        defaultDocumentStoreShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version is greater than or equal to DEFAULT_VERSION
        defaultDocumentStoreShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the documentStoreList where version is greater than or equal to UPDATED_VERSION
        defaultDocumentStoreShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version is less than or equal to DEFAULT_VERSION
        defaultDocumentStoreShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the documentStoreList where version is less than or equal to SMALLER_VERSION
        defaultDocumentStoreShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version is less than DEFAULT_VERSION
        defaultDocumentStoreShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the documentStoreList where version is less than UPDATED_VERSION
        defaultDocumentStoreShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where version is greater than DEFAULT_VERSION
        defaultDocumentStoreShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the documentStoreList where version is greater than SMALLER_VERSION
        defaultDocumentStoreShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where owner equals to DEFAULT_OWNER
        defaultDocumentStoreShouldBeFound("owner.equals=" + DEFAULT_OWNER);

        // Get all the documentStoreList where owner equals to UPDATED_OWNER
        defaultDocumentStoreShouldNotBeFound("owner.equals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByOwnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where owner not equals to DEFAULT_OWNER
        defaultDocumentStoreShouldNotBeFound("owner.notEquals=" + DEFAULT_OWNER);

        // Get all the documentStoreList where owner not equals to UPDATED_OWNER
        defaultDocumentStoreShouldBeFound("owner.notEquals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where owner in DEFAULT_OWNER or UPDATED_OWNER
        defaultDocumentStoreShouldBeFound("owner.in=" + DEFAULT_OWNER + "," + UPDATED_OWNER);

        // Get all the documentStoreList where owner equals to UPDATED_OWNER
        defaultDocumentStoreShouldNotBeFound("owner.in=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where owner is not null
        defaultDocumentStoreShouldBeFound("owner.specified=true");

        // Get all the documentStoreList where owner is null
        defaultDocumentStoreShouldNotBeFound("owner.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentStoresByOwnerContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where owner contains DEFAULT_OWNER
        defaultDocumentStoreShouldBeFound("owner.contains=" + DEFAULT_OWNER);

        // Get all the documentStoreList where owner contains UPDATED_OWNER
        defaultDocumentStoreShouldNotBeFound("owner.contains=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where owner does not contain DEFAULT_OWNER
        defaultDocumentStoreShouldNotBeFound("owner.doesNotContain=" + DEFAULT_OWNER);

        // Get all the documentStoreList where owner does not contain UPDATED_OWNER
        defaultDocumentStoreShouldBeFound("owner.doesNotContain=" + UPDATED_OWNER);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresByResponsibleIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where responsible equals to DEFAULT_RESPONSIBLE
        defaultDocumentStoreShouldBeFound("responsible.equals=" + DEFAULT_RESPONSIBLE);

        // Get all the documentStoreList where responsible equals to UPDATED_RESPONSIBLE
        defaultDocumentStoreShouldNotBeFound("responsible.equals=" + UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByResponsibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where responsible not equals to DEFAULT_RESPONSIBLE
        defaultDocumentStoreShouldNotBeFound("responsible.notEquals=" + DEFAULT_RESPONSIBLE);

        // Get all the documentStoreList where responsible not equals to UPDATED_RESPONSIBLE
        defaultDocumentStoreShouldBeFound("responsible.notEquals=" + UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByResponsibleIsInShouldWork() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where responsible in DEFAULT_RESPONSIBLE or UPDATED_RESPONSIBLE
        defaultDocumentStoreShouldBeFound("responsible.in=" + DEFAULT_RESPONSIBLE + "," + UPDATED_RESPONSIBLE);

        // Get all the documentStoreList where responsible equals to UPDATED_RESPONSIBLE
        defaultDocumentStoreShouldNotBeFound("responsible.in=" + UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByResponsibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where responsible is not null
        defaultDocumentStoreShouldBeFound("responsible.specified=true");

        // Get all the documentStoreList where responsible is null
        defaultDocumentStoreShouldNotBeFound("responsible.specified=false");
    }
                @Test
    @Transactional
    public void getAllDocumentStoresByResponsibleContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where responsible contains DEFAULT_RESPONSIBLE
        defaultDocumentStoreShouldBeFound("responsible.contains=" + DEFAULT_RESPONSIBLE);

        // Get all the documentStoreList where responsible contains UPDATED_RESPONSIBLE
        defaultDocumentStoreShouldNotBeFound("responsible.contains=" + UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void getAllDocumentStoresByResponsibleNotContainsSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

        // Get all the documentStoreList where responsible does not contain DEFAULT_RESPONSIBLE
        defaultDocumentStoreShouldNotBeFound("responsible.doesNotContain=" + DEFAULT_RESPONSIBLE);

        // Get all the documentStoreList where responsible does not contain UPDATED_RESPONSIBLE
        defaultDocumentStoreShouldBeFound("responsible.doesNotContain=" + UPDATED_RESPONSIBLE);
    }


    @Test
    @Transactional
    public void getAllDocumentStoresByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);
        DocumentStore parent = DocumentStoreResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        documentStore.setParent(parent);
        documentStoreRepository.saveAndFlush(documentStore);
        Long parentId = parent.getId();

        // Get all the documentStoreList where parent equals to parentId
        defaultDocumentStoreShouldBeFound("parentId.equals=" + parentId);

        // Get all the documentStoreList where parent equals to parentId + 1
        defaultDocumentStoreShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentStoreShouldBeFound(String filter) throws Exception {
        restDocumentStoreMockMvc.perform(get("/api/document-stores?sort=id,desc&" + filter))
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

        // Check, that the count call also returns 1
        restDocumentStoreMockMvc.perform(get("/api/document-stores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentStoreShouldNotBeFound(String filter) throws Exception {
        restDocumentStoreMockMvc.perform(get("/api/document-stores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentStoreMockMvc.perform(get("/api/document-stores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        documentStoreRepository.saveAndFlush(documentStore);

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
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(updatedDocumentStore);

        restDocumentStoreMockMvc.perform(put("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
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

        // Create the DocumentStore
        DocumentStoreDTO documentStoreDTO = documentStoreMapper.toDto(documentStore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStoreMockMvc.perform(put("/api/document-stores").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStore in the database
        List<DocumentStore> documentStoreList = documentStoreRepository.findAll();
        assertThat(documentStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentStore() throws Exception {
        // Initialize the database
        documentStoreRepository.saveAndFlush(documentStore);

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
