package com.synsoft.xerus.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.synsoft.xerus.domain.DocumentStore;
import com.synsoft.xerus.domain.*; // for static metamodels
import com.synsoft.xerus.repository.DocumentStoreRepository;
import com.synsoft.xerus.service.dto.DocumentStoreCriteria;
import com.synsoft.xerus.service.dto.DocumentStoreDTO;
import com.synsoft.xerus.service.mapper.DocumentStoreMapper;

/**
 * Service for executing complex queries for {@link DocumentStore} entities in the database.
 * The main input is a {@link DocumentStoreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentStoreDTO} or a {@link Page} of {@link DocumentStoreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentStoreQueryService extends QueryService<DocumentStore> {

    private final Logger log = LoggerFactory.getLogger(DocumentStoreQueryService.class);

    private final DocumentStoreRepository documentStoreRepository;

    private final DocumentStoreMapper documentStoreMapper;

    public DocumentStoreQueryService(DocumentStoreRepository documentStoreRepository, DocumentStoreMapper documentStoreMapper) {
        this.documentStoreRepository = documentStoreRepository;
        this.documentStoreMapper = documentStoreMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentStoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentStoreDTO> findByCriteria(DocumentStoreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DocumentStore> specification = createSpecification(criteria);
        return documentStoreMapper.toDto(documentStoreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentStoreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentStoreDTO> findByCriteria(DocumentStoreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentStore> specification = createSpecification(criteria);
        return documentStoreRepository.findAll(specification, page)
            .map(documentStoreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentStoreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DocumentStore> specification = createSpecification(criteria);
        return documentStoreRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentStoreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentStore> createSpecification(DocumentStoreCriteria criteria) {
        Specification<DocumentStore> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentStore_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DocumentStore_.name));
            }
            if (criteria.getExtension() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtension(), DocumentStore_.extension));
            }
            if (criteria.getSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSize(), DocumentStore_.size));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), DocumentStore_.status));
            }
            if (criteria.getIsFolder() != null) {
                specification = specification.and(buildSpecification(criteria.getIsFolder(), DocumentStore_.isFolder));
            }
            if (criteria.getVersionControlled() != null) {
                specification = specification.and(buildSpecification(criteria.getVersionControlled(), DocumentStore_.versionControlled));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), DocumentStore_.version));
            }
            if (criteria.getOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwner(), DocumentStore_.owner));
            }
            if (criteria.getResponsible() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponsible(), DocumentStore_.responsible));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(DocumentStore_.parent, JoinType.LEFT).get(DocumentStore_.id)));
            }
        }
        return specification;
    }
}
