package com.synsoft.xerus.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.synsoft.xerus.domain.enumeration.DocumentStatus;

/**
 * A DocumentStore.
 */
@Entity
@Table(name = "document_store")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentStore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private Long size;

    @Lob
    @Column(name = "doc")
    private byte[] doc;

    @Column(name = "doc_content_type")
    private String docContentType;

    @Lob
    @Column(name = "thumbnail")
    private byte[] thumbnail;

    @Column(name = "thumbnail_content_type")
    private String thumbnailContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DocumentStatus status;

    @Column(name = "is_folder")
    private Boolean isFolder;

    @Column(name = "version_controlled")
    private Boolean versionControlled;

    @Column(name = "version")
    private Long version;

    @Column(name = "owner")
    private String owner;

    @Column(name = "responsible")
    private String responsible;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DocumentStore name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public DocumentStore extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public DocumentStore size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getDoc() {
        return doc;
    }

    public DocumentStore doc(byte[] doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public DocumentStore docContentType(String docContentType) {
        this.docContentType = docContentType;
        return this;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public DocumentStore thumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailContentType() {
        return thumbnailContentType;
    }

    public DocumentStore thumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
        return this;
    }

    public void setThumbnailContentType(String thumbnailContentType) {
        this.thumbnailContentType = thumbnailContentType;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public DocumentStore status(DocumentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Boolean isIsFolder() {
        return isFolder;
    }

    public DocumentStore isFolder(Boolean isFolder) {
        this.isFolder = isFolder;
        return this;
    }

    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public Boolean isVersionControlled() {
        return versionControlled;
    }

    public DocumentStore versionControlled(Boolean versionControlled) {
        this.versionControlled = versionControlled;
        return this;
    }

    public void setVersionControlled(Boolean versionControlled) {
        this.versionControlled = versionControlled;
    }

    public Long getVersion() {
        return version;
    }

    public DocumentStore version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public DocumentStore owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getResponsible() {
        return responsible;
    }

    public DocumentStore responsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentStore)) {
            return false;
        }
        return id != null && id.equals(((DocumentStore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentStore{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", extension='" + getExtension() + "'" +
            ", size=" + getSize() +
            ", doc='" + getDoc() + "'" +
            ", docContentType='" + getDocContentType() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", thumbnailContentType='" + getThumbnailContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", isFolder='" + isIsFolder() + "'" +
            ", versionControlled='" + isVersionControlled() + "'" +
            ", version=" + getVersion() +
            ", owner='" + getOwner() + "'" +
            ", responsible='" + getResponsible() + "'" +
            "}";
    }
}
