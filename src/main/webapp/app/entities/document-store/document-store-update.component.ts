import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDocumentStore, DocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from './document-store.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-document-store-update',
  templateUrl: './document-store-update.component.html',
})
export class DocumentStoreUpdateComponent implements OnInit {
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
    owner: [],
    responsible: [],
    parent: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected documentStoreService: DocumentStoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStore }) => {
      this.updateForm(documentStore);

      this.documentStoreService.query().subscribe((res: HttpResponse<IDocumentStore[]>) => (this.documentstores = res.body || []));
    });
  }

  updateForm(documentStore: IDocumentStore): void {
    this.editForm.patchValue({
      id: documentStore.id,
      name: documentStore.name,
      extension: documentStore.extension,
      contentType: documentStore.contentType,
      size: documentStore.size,
      doc: documentStore.doc,
      docContentType: documentStore.docContentType,
      status: documentStore.status,
      isFolder: documentStore.isFolder,
      owner: documentStore.owner,
      responsible: documentStore.responsible,
      parent: documentStore.parent,
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
    const documentStore = this.createFromForm();
    if (documentStore.id !== undefined) {
      this.subscribeToSaveResponse(this.documentStoreService.update(documentStore));
    } else {
      this.subscribeToSaveResponse(this.documentStoreService.create(documentStore));
    }
  }

  private createFromForm(): IDocumentStore {
    return {
      ...new DocumentStore(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      extension: this.editForm.get(['extension'])!.value,
      contentType: this.editForm.get(['contentType'])!.value,
      size: this.editForm.get(['size'])!.value,
      docContentType: this.editForm.get(['docContentType'])!.value,
      doc: this.editForm.get(['doc'])!.value,
      status: this.editForm.get(['status'])!.value,
      isFolder: this.editForm.get(['isFolder'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      responsible: this.editForm.get(['responsible'])!.value,
      parent: this.editForm.get(['parent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentStore>>): void {
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