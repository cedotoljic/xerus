package com.synsoft.xerus.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentStoreHistoryMapperTest {

    private DocumentStoreHistoryMapper documentStoreHistoryMapper;

    @BeforeEach
    public void setUp() {
        documentStoreHistoryMapper = new DocumentStoreHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(documentStoreHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentStoreHistoryMapper.fromId(null)).isNull();
    }
}
