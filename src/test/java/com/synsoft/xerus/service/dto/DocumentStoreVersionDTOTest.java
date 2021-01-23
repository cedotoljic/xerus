package com.synsoft.xerus.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synsoft.xerus.web.rest.TestUtil;

public class DocumentStoreVersionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStoreVersionDTO.class);
        DocumentStoreVersionDTO documentStoreVersionDTO1 = new DocumentStoreVersionDTO();
        documentStoreVersionDTO1.setId(1L);
        DocumentStoreVersionDTO documentStoreVersionDTO2 = new DocumentStoreVersionDTO();
        assertThat(documentStoreVersionDTO1).isNotEqualTo(documentStoreVersionDTO2);
        documentStoreVersionDTO2.setId(documentStoreVersionDTO1.getId());
        assertThat(documentStoreVersionDTO1).isEqualTo(documentStoreVersionDTO2);
        documentStoreVersionDTO2.setId(2L);
        assertThat(documentStoreVersionDTO1).isNotEqualTo(documentStoreVersionDTO2);
        documentStoreVersionDTO1.setId(null);
        assertThat(documentStoreVersionDTO1).isNotEqualTo(documentStoreVersionDTO2);
    }
}
