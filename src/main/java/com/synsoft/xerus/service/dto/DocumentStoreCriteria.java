package com.synsoft.xerus.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.synsoft.xerus.domain.enumeration.DocumentStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.synsoft.xerus.domain.DocumentStore} entity. This class is used
 * in {@link com.synsoft.xerus.web.rest.DocumentStoreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-stores?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocumentStoreCriteria implements Serializable, Criteria {
    /**
     * Class for filtering DocumentStatus
     */
    public static class DocumentStatusFilter extends Filter<DocumentStatus> {

        public DocumentStatusFilter() {
        }

        public DocumentStatusFilter(DocumentStatusFilter filter) {
            super(filter);
        }

        @Override
        public DocumentStatusFilter copy() {
            return new DocumentStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter extension;

    private LongFilter size;

    private DocumentStatusFilter status;

    private BooleanFilter isFolder;

    private BooleanFilter versionControlled;

    private LongFilter version;

    private StringFilter owner;

    private StringFilter responsible;

    public DocumentStoreCriteria() {
    }

    public DocumentStoreCriteria(DocumentStoreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.extension = other.extension == null ? null : other.extension.copy();
        this.size = other.size == null ? null : other.size.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.isFolder = other.isFolder == null ? null : other.isFolder.copy();
        this.versionControlled = other.versionControlled == null ? null : other.versionControlled.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.owner = other.owner == null ? null : other.owner.copy();
        this.responsible = other.responsible == null ? null : other.responsible.copy();
    }

    @Override
    public DocumentStoreCriteria copy() {
        return new DocumentStoreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getExtension() {
        return extension;
    }

    public void setExtension(StringFilter extension) {
        this.extension = extension;
    }

    public LongFilter getSize() {
        return size;
    }

    public void setSize(LongFilter size) {
        this.size = size;
    }

    public DocumentStatusFilter getStatus() {
        return status;
    }

    public void setStatus(DocumentStatusFilter status) {
        this.status = status;
    }

    public BooleanFilter getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(BooleanFilter isFolder) {
        this.isFolder = isFolder;
    }

    public BooleanFilter getVersionControlled() {
        return versionControlled;
    }

    public void setVersionControlled(BooleanFilter versionControlled) {
        this.versionControlled = versionControlled;
    }

    public LongFilter getVersion() {
        return version;
    }

    public void setVersion(LongFilter version) {
        this.version = version;
    }

    public StringFilter getOwner() {
        return owner;
    }

    public void setOwner(StringFilter owner) {
        this.owner = owner;
    }

    public StringFilter getResponsible() {
        return responsible;
    }

    public void setResponsible(StringFilter responsible) {
        this.responsible = responsible;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentStoreCriteria that = (DocumentStoreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(extension, that.extension) &&
            Objects.equals(size, that.size) &&
            Objects.equals(status, that.status) &&
            Objects.equals(isFolder, that.isFolder) &&
            Objects.equals(versionControlled, that.versionControlled) &&
            Objects.equals(version, that.version) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(responsible, that.responsible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        extension,
        size,
        status,
        isFolder,
        versionControlled,
        version,
        owner,
        responsible
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentStoreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (extension != null ? "extension=" + extension + ", " : "") +
                (size != null ? "size=" + size + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (isFolder != null ? "isFolder=" + isFolder + ", " : "") +
                (versionControlled != null ? "versionControlled=" + versionControlled + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (owner != null ? "owner=" + owner + ", " : "") +
                (responsible != null ? "responsible=" + responsible + ", " : "") +
            "}";
    }

}
