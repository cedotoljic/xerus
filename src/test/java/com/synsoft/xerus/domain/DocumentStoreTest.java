package com.synsoft.xerus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synsoft.xerus.web.rest.TestUtil;

public class DocumentStoreTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStore.class);
        DocumentStore documentStore1 = new DocumentStore();
        documentStore1.setId(1L);
        DocumentStore documentStore2 = new DocumentStore();
        documentStore2.setId(documentStore1.getId());
        assertThat(documentStore1).isEqualTo(documentStore2);
        documentStore2.setId(2L);
        assertThat(documentStore1).isNotEqualTo(documentStore2);
        documentStore1.setId(null);
        assertThat(documentStore1).isNotEqualTo(documentStore2);
    }
}
