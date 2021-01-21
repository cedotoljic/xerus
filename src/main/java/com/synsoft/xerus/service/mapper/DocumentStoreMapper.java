package com.synsoft.xerus.service.mapper;


import com.synsoft.xerus.domain.*;
import com.synsoft.xerus.service.dto.DocumentStoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentStore} and its DTO {@link DocumentStoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentStoreMapper extends EntityMapper<DocumentStoreDTO, DocumentStore> {

    @Mapping(source = "parent.id", target = "parentId")
    DocumentStoreDTO toDto(DocumentStore documentStore);

    @Mapping(source = "parentId", target = "parent")
    DocumentStore toEntity(DocumentStoreDTO documentStoreDTO);

    default DocumentStore fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentStore documentStore = new DocumentStore();
        documentStore.setId(id);
        return documentStore;
    }
}
