package com.synsoft.xerus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synsoft.xerus.web.rest.TestUtil;

public class DocumentStoreHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStoreHistory.class);
        DocumentStoreHistory documentStoreHistory1 = new DocumentStoreHistory();
        documentStoreHistory1.setId(1L);
        DocumentStoreHistory documentStoreHistory2 = new DocumentStoreHistory();
        documentStoreHistory2.setId(documentStoreHistory1.getId());
        assertThat(documentStoreHistory1).isEqualTo(documentStoreHistory2);
        documentStoreHistory2.setId(2L);
        assertThat(documentStoreHistory1).isNotEqualTo(documentStoreHistory2);
        documentStoreHistory1.setId(null);
        assertThat(documentStoreHistory1).isNotEqualTo(documentStoreHistory2);
    }
}
