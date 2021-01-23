package com.synsoft.xerus.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentStoreVersionMapperTest {

    private DocumentStoreVersionMapper documentStoreVersionMapper;

    @BeforeEach
    public void setUp() {
        documentStoreVersionMapper = new DocumentStoreVersionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(documentStoreVersionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentStoreVersionMapper.fromId(null)).isNull();
    }
}
