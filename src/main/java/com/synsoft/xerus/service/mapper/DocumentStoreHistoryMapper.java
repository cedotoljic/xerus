package com.synsoft.xerus.service.mapper;


import com.synsoft.xerus.domain.*;
import com.synsoft.xerus.service.dto.DocumentStoreHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentStoreHistory} and its DTO {@link DocumentStoreHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentStoreVersionMapper.class})
public interface DocumentStoreHistoryMapper extends EntityMapper<DocumentStoreHistoryDTO, DocumentStoreHistory> {

    @Mapping(source = "document.id", target = "documentId")
    DocumentStoreHistoryDTO toDto(DocumentStoreHistory documentStoreHistory);

    @Mapping(source = "documentId", target = "document")
    DocumentStoreHistory toEntity(DocumentStoreHistoryDTO documentStoreHistoryDTO);

    default DocumentStoreHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentStoreHistory documentStoreHistory = new DocumentStoreHistory();
        documentStoreHistory.setId(id);
        return documentStoreHistory;
    }
}
