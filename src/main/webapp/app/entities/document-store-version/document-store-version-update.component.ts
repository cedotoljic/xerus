import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDocumentStoreVersion, DocumentStoreVersion } from 'app/shared/model/document-store-version.model';
import { DocumentStoreVersionService } from './document-store-version.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from 'app/entities/document-store/document-store.service';

@Component({
  selector: 'jhi-document-store-version-update',
  templateUrl: './document-store-version-update.component.html',
})
export class DocumentStoreVersionUpdateComponent implements OnInit {
  isSaving = false;
  documentstores: IDocumentStore[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    extension: [],
    contentType: [],
    size: [],
    doc: [],
    docContentType: [],
    status: [],
    isFolder: [],
    versionControlled: [],
    version: [],
    owner: [],
    responsible: [],
    document: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected documentStoreVersionService: DocumentStoreVersionService,
    protected documentStoreService: DocumentStoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStoreVersion }) => {
      this.updateForm(documentStoreVersion);

      this.documentStoreService.query().subscribe((res: HttpResponse<IDocumentStore[]>) => (this.documentstores = res.body || []));
    });
  }

  updateForm(documentStoreVersion: IDocumentStoreVersion): void {
    this.editForm.patchValue({
      id: documentStoreVersion.id,
      name: documentStoreVersion.name,
      extension: documentStoreVersion.extension,
      contentType: documentStoreVersion.contentType,
      size: documentStoreVersion.size,
      doc: documentStoreVersion.doc,
      docContentType: documentStoreVersion.docContentType,
      status: documentStoreVersion.status,
      isFolder: documentStoreVersion.isFolder,
      versionControlled: documentStoreVersion.versionControlled,
      version: documentStoreVersion.version,
      owner: documentStoreVersion.owner,
      responsible: documentStoreVersion.responsible,
      document: documentStoreVersion.document,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('xerusApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const documentStoreVersion = this.createFromForm();
    if (documentStoreVersion.id !== undefined) {
      this.subscribeToSaveResponse(this.documentStoreVersionService.update(documentStoreVersion));
    } else {
      this.subscribeToSaveResponse(this.documentStoreVersionService.create(documentStoreVersion));
    }
  }

  private createFromForm(): IDocumentStoreVersion {
    return {
      ...new DocumentStoreVersion(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      extension: this.editForm.get(['extension'])!.value,
      contentType: this.editForm.get(['contentType'])!.value,
      size: this.editForm.get(['size'])!.value,
      docContentType: this.editForm.get(['docContentType'])!.value,
      doc: this.editForm.get(['doc'])!.value,
      status: this.editForm.get(['status'])!.value,
      isFolder: this.editForm.get(['isFolder'])!.value,
      versionControlled: this.editForm.get(['versionControlled'])!.value,
      version: this.editForm.get(['version'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      responsible: this.editForm.get(['responsible'])!.value,
      document: this.editForm.get(['document'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentStoreVersion>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IDocumentStore): any {
    return item.id;
  }
}
