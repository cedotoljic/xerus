package com.synsoft.xerus.repository;

import com.synsoft.xerus.domain.DocumentStoreHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DocumentStoreHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentStoreHistoryRepository extends JpaRepository<DocumentStoreHistory, Long> {
}
