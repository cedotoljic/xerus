package com.synsoft.xerus.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentStoreMapperTest {

    private DocumentStoreMapper documentStoreMapper;

    @BeforeEach
    public void setUp() {
        documentStoreMapper = new DocumentStoreMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(documentStoreMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentStoreMapper.fromId(null)).isNull();
    }
}
