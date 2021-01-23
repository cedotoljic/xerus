package com.synsoft.xerus.web.rest;

import com.synsoft.xerus.XerusApp;
import com.synsoft.xerus.config.TestSecurityConfiguration;
import com.synsoft.xerus.domain.DocumentStoreVersion;
import com.synsoft.xerus.repository.DocumentStoreVersionRepository;
import com.synsoft.xerus.service.DocumentStoreVersionService;
import com.synsoft.xerus.service.dto.DocumentStoreVersionDTO;
import com.synsoft.xerus.service.mapper.DocumentStoreVersionMapper;

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
 * Integration tests for the {@link DocumentStoreVersionResource} REST controller.
 */
@SpringBootTest(classes = { XerusApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DocumentStoreVersionResourceIT {

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
    private DocumentStoreVersionRepository documentStoreVersionRepository;

    @Autowired
    private DocumentStoreVersionMapper documentStoreVersionMapper;

    @Autowired
    private DocumentStoreVersionService documentStoreVersionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentStoreVersionMockMvc;

    private DocumentStoreVersion documentStoreVersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStoreVersion createEntity(EntityManager em) {
        DocumentStoreVersion documentStoreVersion = new DocumentStoreVersion()
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
        return documentStoreVersion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentStoreVersion createUpdatedEntity(EntityManager em) {
        DocumentStoreVersion documentStoreVersion = new DocumentStoreVersion()
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
        return documentStoreVersion;
    }

    @BeforeEach
    public void initTest() {
        documentStoreVersion = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentStoreVersion() throws Exception {
        int databaseSizeBeforeCreate = documentStoreVersionRepository.findAll().size();
        // Create the DocumentStoreVersion
        DocumentStoreVersionDTO documentStoreVersionDTO = documentStoreVersionMapper.toDto(documentStoreVersion);
        restDocumentStoreVersionMockMvc.perform(post("/api/document-store-versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreVersionDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentStoreVersion in the database
        List<DocumentStoreVersion> documentStoreVersionList = documentStoreVersionRepository.findAll();
        assertThat(documentStoreVersionList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentStoreVersion testDocumentStoreVersion = documentStoreVersionList.get(documentStoreVersionList.size() - 1);
        assertThat(testDocumentStoreVersion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDocumentStoreVersion.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testDocumentStoreVersion.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testDocumentStoreVersion.getDoc()).isEqualTo(DEFAULT_DOC);
        assertThat(testDocumentStoreVersion.getDocContentType()).isEqualTo(DEFAULT_DOC_CONTENT_TYPE);
        assertThat(testDocumentStoreVersion.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testDocumentStoreVersion.getThumbnailContentType()).isEqualTo(DEFAULT_THUMBNAIL_CONTENT_TYPE);
        assertThat(testDocumentStoreVersion.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDocumentStoreVersion.isIsFolder()).isEqualTo(DEFAULT_IS_FOLDER);
        assertThat(testDocumentStoreVersion.isVersionControlled()).isEqualTo(DEFAULT_VERSION_CONTROLLED);
        assertThat(testDocumentStoreVersion.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testDocumentStoreVersion.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testDocumentStoreVersion.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void createDocumentStoreVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentStoreVersionRepository.findAll().size();

        // Create the DocumentStoreVersion with an existing ID
        documentStoreVersion.setId(1L);
        DocumentStoreVersionDTO documentStoreVersionDTO = documentStoreVersionMapper.toDto(documentStoreVersion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentStoreVersionMockMvc.perform(post("/api/document-store-versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStoreVersion in the database
        List<DocumentStoreVersion> documentStoreVersionList = documentStoreVersionRepository.findAll();
        assertThat(documentStoreVersionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentStoreVersions() throws Exception {
        // Initialize the database
        documentStoreVersionRepository.saveAndFlush(documentStoreVersion);

        // Get all the documentStoreVersionList
        restDocumentStoreVersionMockMvc.perform(get("/api/document-store-versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentStoreVersion.getId().intValue())))
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
    public void getDocumentStoreVersion() throws Exception {
        // Initialize the database
        documentStoreVersionRepository.saveAndFlush(documentStoreVersion);

        // Get the documentStoreVersion
        restDocumentStoreVersionMockMvc.perform(get("/api/document-store-versions/{id}", documentStoreVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentStoreVersion.getId().intValue()))
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
    public void getNonExistingDocumentStoreVersion() throws Exception {
        // Get the documentStoreVersion
        restDocumentStoreVersionMockMvc.perform(get("/api/document-store-versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentStoreVersion() throws Exception {
        // Initialize the database
        documentStoreVersionRepository.saveAndFlush(documentStoreVersion);

        int databaseSizeBeforeUpdate = documentStoreVersionRepository.findAll().size();

        // Update the documentStoreVersion
        DocumentStoreVersion updatedDocumentStoreVersion = documentStoreVersionRepository.findById(documentStoreVersion.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentStoreVersion are not directly saved in db
        em.detach(updatedDocumentStoreVersion);
        updatedDocumentStoreVersion
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
        DocumentStoreVersionDTO documentStoreVersionDTO = documentStoreVersionMapper.toDto(updatedDocumentStoreVersion);

        restDocumentStoreVersionMockMvc.perform(put("/api/document-store-versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreVersionDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentStoreVersion in the database
        List<DocumentStoreVersion> documentStoreVersionList = documentStoreVersionRepository.findAll();
        assertThat(documentStoreVersionList).hasSize(databaseSizeBeforeUpdate);
        DocumentStoreVersion testDocumentStoreVersion = documentStoreVersionList.get(documentStoreVersionList.size() - 1);
        assertThat(testDocumentStoreVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDocumentStoreVersion.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testDocumentStoreVersion.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testDocumentStoreVersion.getDoc()).isEqualTo(UPDATED_DOC);
        assertThat(testDocumentStoreVersion.getDocContentType()).isEqualTo(UPDATED_DOC_CONTENT_TYPE);
        assertThat(testDocumentStoreVersion.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testDocumentStoreVersion.getThumbnailContentType()).isEqualTo(UPDATED_THUMBNAIL_CONTENT_TYPE);
        assertThat(testDocumentStoreVersion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDocumentStoreVersion.isIsFolder()).isEqualTo(UPDATED_IS_FOLDER);
        assertThat(testDocumentStoreVersion.isVersionControlled()).isEqualTo(UPDATED_VERSION_CONTROLLED);
        assertThat(testDocumentStoreVersion.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testDocumentStoreVersion.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testDocumentStoreVersion.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentStoreVersion() throws Exception {
        int databaseSizeBeforeUpdate = documentStoreVersionRepository.findAll().size();

        // Create the DocumentStoreVersion
        DocumentStoreVersionDTO documentStoreVersionDTO = documentStoreVersionMapper.toDto(documentStoreVersion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentStoreVersionMockMvc.perform(put("/api/document-store-versions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(documentStoreVersionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentStoreVersion in the database
        List<DocumentStoreVersion> documentStoreVersionList = documentStoreVersionRepository.findAll();
        assertThat(documentStoreVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentStoreVersion() throws Exception {
        // Initialize the database
        documentStoreVersionRepository.saveAndFlush(documentStoreVersion);

        int databaseSizeBeforeDelete = documentStoreVersionRepository.findAll().size();

        // Delete the documentStoreVersion
        restDocumentStoreVersionMockMvc.perform(delete("/api/document-store-versions/{id}", documentStoreVersion.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentStoreVersion> documentStoreVersionList = documentStoreVersionRepository.findAll();
        assertThat(documentStoreVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
