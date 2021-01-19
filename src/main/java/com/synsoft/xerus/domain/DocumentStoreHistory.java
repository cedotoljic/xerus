package com.synsoft.xerus.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.synsoft.xerus.domain.enumeration.DocumentStatus;

/**
 * A DocumentStoreHistory.
 */
@Entity
@Table(name = "document_store_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DocumentStoreHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Lob
    @Column(name = "doc")
    private byte[] doc;

    @Column(name = "doc_content_type")
    private String docContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DocumentStatus status;

    @Column(name = "is_folder")
    private Boolean isFolder;

    @Column(name = "owner")
    private String owner;

    @Column(name = "responsible")
    private String responsible;

    @ManyToOne
    @JsonIgnoreProperties(value = "documentStoreHistories", allowSetters = true)
    private DocumentStore document;

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

    public DocumentStoreHistory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public DocumentStoreHistory extension(String extension) {
        this.extension = extension;
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public DocumentStoreHistory contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public DocumentStoreHistory size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getDoc() {
        return doc;
    }

    public DocumentStoreHistory doc(byte[] doc) {
        this.doc = doc;
        return this;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public String getDocContentType() {
        return docContentType;
    }

    public DocumentStoreHistory docContentType(String docContentType) {
        this.docContentType = docContentType;
        return this;
    }

    public void setDocContentType(String docContentType) {
        this.docContentType = docContentType;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public DocumentStoreHistory status(DocumentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Boolean isIsFolder() {
        return isFolder;
    }

    public DocumentStoreHistory isFolder(Boolean isFolder) {
        this.isFolder = isFolder;
        return this;
    }

    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

    public String getOwner() {
        return owner;
    }

    public DocumentStoreHistory owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getResponsible() {
        return responsible;
    }

    public DocumentStoreHistory responsible(String responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public DocumentStore getDocument() {
        return document;
    }

    public DocumentStoreHistory document(DocumentStore documentStore) {
        this.document = documentStore;
        return this;
    }

    public void setDocument(DocumentStore documentStore) {
        this.document = documentStore;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentStoreHistory)) {
            return false;
        }
        return id != null && id.equals(((DocumentStoreHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentStoreHistory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", extension='" + getExtension() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", size=" + getSize() +
            ", doc='" + getDoc() + "'" +
            ", docContentType='" + getDocContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", isFolder='" + isIsFolder() + "'" +
            ", owner='" + getOwner() + "'" +
            ", responsible='" + getResponsible() + "'" +
            "}";
    }
}