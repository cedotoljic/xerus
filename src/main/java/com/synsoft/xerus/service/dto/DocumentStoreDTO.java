package com.synsoft.xerus.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;
import com.synsoft.xerus.domain.enumeration.DocumentStatus;

/**
 * A DTO for the {@link com.synsoft.xerus.domain.DocumentStore} entity.
 */
public class DocumentStoreDTO implements Serializable {
    
    private Long id;

    private String name;

    private String extension;

    private Long size;

    @Lob
    private byte[] doc;

    private String docContentType;
    @Lob
    private byte[] thumbnail;

    private String thumbnailContentType;
    private DocumentStatus status;

    private Boolean isFolder;

    private Boolean versionControlled;

    private Long version;

    private String owner;

    private String responsible;


    private Long parentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getDoc() {
        return doc;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailContentType() {
        return thumbnailContentType;
    }

    public void setThumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Boolean isIsFolder() {
        return isFolder;
    }

    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public Boolean isVersionControlled() {
        return versionControlled;
    }

    public void setVersionControlled(Boolean versionControlled) {
        this.versionControlled = versionControlled;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long documentStoreId) {
        this.parentId = documentStoreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentStoreDTO)) {
            return false;
        }

        return id != null && id.equals(((DocumentStoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentStoreDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", extension='" + getExtension() + "'" +
            ", size=" + getSize() +
            ", doc='" + getDoc() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", status='" + getStatus() + "'" +
            ", isFolder='" + isIsFolder() + "'" +
            ", versionControlled='" + isVersionControlled() + "'" +
            ", version=" + getVersion() +
            ", owner='" + getOwner() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", parentId=" + getParentId() +
            "}";
    }
}
