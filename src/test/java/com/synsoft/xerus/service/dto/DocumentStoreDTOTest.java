package com.synsoft.xerus.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synsoft.xerus.web.rest.TestUtil;

public class DocumentStoreDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStoreDTO.class);
        DocumentStoreDTO documentStoreDTO1 = new DocumentStoreDTO();
        documentStoreDTO1.setId(1L);
        DocumentStoreDTO documentStoreDTO2 = new DocumentStoreDTO();
        assertThat(documentStoreDTO1).isNotEqualTo(documentStoreDTO2);
        documentStoreDTO2.setId(documentStoreDTO1.getId());
        assertThat(documentStoreDTO1).isEqualTo(documentStoreDTO2);
        documentStoreDTO2.setId(2L);
        assertThat(documentStoreDTO1).isNotEqualTo(documentStoreDTO2);
        documentStoreDTO1.setId(null);
        assertThat(documentStoreDTO1).isNotEqualTo(documentStoreDTO2);
    }
}
