package com.synsoft.xerus.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synsoft.xerus.web.rest.TestUtil;

public class DocumentStoreHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStoreHistoryDTO.class);
        DocumentStoreHistoryDTO documentStoreHistoryDTO1 = new DocumentStoreHistoryDTO();
        documentStoreHistoryDTO1.setId(1L);
        DocumentStoreHistoryDTO documentStoreHistoryDTO2 = new DocumentStoreHistoryDTO();
        assertThat(documentStoreHistoryDTO1).isNotEqualTo(documentStoreHistoryDTO2);
        documentStoreHistoryDTO2.setId(documentStoreHistoryDTO1.getId());
        assertThat(documentStoreHistoryDTO1).isEqualTo(documentStoreHistoryDTO2);
        documentStoreHistoryDTO2.setId(2L);
        assertThat(documentStoreHistoryDTO1).isNotEqualTo(documentStoreHistoryDTO2);
        documentStoreHistoryDTO1.setId(null);
        assertThat(documentStoreHistoryDTO1).isNotEqualTo(documentStoreHistoryDTO2);
    }
}
