package com.synsoft.xerus.service.mapper;


import com.synsoft.xerus.domain.*;
import com.synsoft.xerus.service.dto.DocumentStoreVersionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentStoreVersion} and its DTO {@link DocumentStoreVersionDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentStoreMapper.class})
public interface DocumentStoreVersionMapper extends EntityMapper<DocumentStoreVersionDTO, DocumentStoreVersion> {

    @Mapping(source = "document.id", target = "documentId")
    DocumentStoreVersionDTO toDto(DocumentStoreVersion documentStoreVersion);

    @Mapping(source = "documentId", target = "document")
    DocumentStoreVersion toEntity(DocumentStoreVersionDTO documentStoreVersionDTO);

    default DocumentStoreVersion fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentStoreVersion documentStoreVersion = new DocumentStoreVersion();
        documentStoreVersion.setId(id);
        return documentStoreVersion;
    }
}
