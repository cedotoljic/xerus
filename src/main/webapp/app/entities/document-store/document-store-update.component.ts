import { Component, OnInit, ElementRef } from '@angular/core';
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
    parentId: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected documentStoreService: DocumentStoreService,
    protected elementRef: ElementRef,
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
      size: documentStore.size,
      doc: documentStore.doc,
      docContentType: documentStore.docContentType,
      thumbnail: documentStore.thumbnail,
      thumbnailContentType: documentStore.thumbnailContentType,
      status: documentStore.status,
      isFolder: documentStore.isFolder,
      versionControlled: documentStore.versionControlled,
      version: documentStore.version,
      owner: documentStore.owner,
      responsible: documentStore.responsible,
      parentId: documentStore.parentId,
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
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
      parentId: this.editForm.get(['parentId'])!.value,
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
