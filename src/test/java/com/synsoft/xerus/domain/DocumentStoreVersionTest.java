package com.synsoft.xerus.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.synsoft.xerus.web.rest.TestUtil;

public class DocumentStoreVersionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentStoreVersion.class);
        DocumentStoreVersion documentStoreVersion1 = new DocumentStoreVersion();
        documentStoreVersion1.setId(1L);
        DocumentStoreVersion documentStoreVersion2 = new DocumentStoreVersion();
        documentStoreVersion2.setId(documentStoreVersion1.getId());
        assertThat(documentStoreVersion1).isEqualTo(documentStoreVersion2);
        documentStoreVersion2.setId(2L);
        assertThat(documentStoreVersion1).isNotEqualTo(documentStoreVersion2);
        documentStoreVersion1.setId(null);
        assertThat(documentStoreVersion1).isNotEqualTo(documentStoreVersion2);
    }
}
