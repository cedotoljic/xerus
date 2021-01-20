package com.synsoft.xerus.repository;

import com.synsoft.xerus.domain.DocumentStoreVersion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentStoreVersion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentStoreVersionRepository extends JpaRepository<DocumentStoreVersion, Long> {
}
