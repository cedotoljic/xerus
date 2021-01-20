package com.synsoft.xerus.repository;

import com.synsoft.xerus.domain.DocumentStore;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentStoreRepository extends JpaRepository<DocumentStore, Long> {
}
