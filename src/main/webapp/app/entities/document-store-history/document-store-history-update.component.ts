import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDocumentStoreHistory, DocumentStoreHistory } from 'app/shared/model/document-store-history.model';
import { DocumentStoreHistoryService } from './document-store-history.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IDocumentStoreVersion } from 'app/shared/model/document-store-version.model';
import { DocumentStoreVersionService } from 'app/entities/document-store-version/document-store-version.service';

@Component({
  selector: 'jhi-document-store-history-update',
  templateUrl: './document-store-history-update.component.html',
})
export class DocumentStoreHistoryUpdateComponent implements OnInit {
  isSaving = false;
  documentstoreversions: IDocumentStoreVersion[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    extension: [],
    contentType: [],
    size: [],
    doc: [],
    docContentType: [],
    thumbnail: [],
    thumbnailContentType: [],
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
    protected documentStoreHistoryService: DocumentStoreHistoryService,
    protected documentStoreVersionService: DocumentStoreVersionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStoreHistory }) => {
      this.updateForm(documentStoreHistory);

      this.documentStoreVersionService
        .query()
        .subscribe((res: HttpResponse<IDocumentStoreVersion[]>) => (this.documentstoreversions = res.body || []));
    });
  }

  updateForm(documentStoreHistory: IDocumentStoreHistory): void {
    this.editForm.patchValue({
      id: documentStoreHistory.id,
      name: documentStoreHistory.name,
      extension: documentStoreHistory.extension,
      contentType: documentStoreHistory.contentType,
      size: documentStoreHistory.size,
      doc: documentStoreHistory.doc,
      docContentType: documentStoreHistory.docContentType,
      thumbnail: documentStoreHistory.thumbnail,
      thumbnailContentType: documentStoreHistory.thumbnailContentType,
      status: documentStoreHistory.status,
      isFolder: documentStoreHistory.isFolder,
      versionControlled: documentStoreHistory.versionControlled,
      version: documentStoreHistory.version,
      owner: documentStoreHistory.owner,
      responsible: documentStoreHistory.responsible,
      document: documentStoreHistory.document,
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
    const documentStoreHistory = this.createFromForm();
    if (documentStoreHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.documentStoreHistoryService.update(documentStoreHistory));
    } else {
      this.subscribeToSaveResponse(this.documentStoreHistoryService.create(documentStoreHistory));
    }
  }

  private createFromForm(): IDocumentStoreHistory {
    return {
      ...new DocumentStoreHistory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      extension: this.editForm.get(['extension'])!.value,
      contentType: this.editForm.get(['contentType'])!.value,
      size: this.editForm.get(['size'])!.value,
      docContentType: this.editForm.get(['docContentType'])!.value,
      doc: this.editForm.get(['doc'])!.value,
      thumbnailContentType: this.editForm.get(['thumbnailContentType'])!.value,
      thumbnail: this.editForm.get(['thumbnail'])!.value,
      status: this.editForm.get(['status'])!.value,
      isFolder: this.editForm.get(['isFolder'])!.value,
      versionControlled: this.editForm.get(['versionControlled'])!.value,
      version: this.editForm.get(['version'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      responsible: this.editForm.get(['responsible'])!.value,
      document: this.editForm.get(['document'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentStoreHistory>>): void {
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

  trackById(index: number, item: IDocumentStoreVersion): any {
    return item.id;
  }
}
