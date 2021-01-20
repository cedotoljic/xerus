import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDocumentStoreVersion } from 'app/shared/model/document-store-version.model';
import { DocumentStoreVersionService } from './document-store-version.service';
import { DocumentStoreVersionDeleteDialogComponent } from './document-store-version-delete-dialog.component';

@Component({
  selector: 'jhi-document-store-version',
  templateUrl: './document-store-version.component.html',
})
export class DocumentStoreVersionComponent implements OnInit, OnDestroy {
  documentStoreVersions?: IDocumentStoreVersion[];
  eventSubscriber?: Subscription;

  constructor(
    protected documentStoreVersionService: DocumentStoreVersionService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.documentStoreVersionService
      .query()
      .subscribe((res: HttpResponse<IDocumentStoreVersion[]>) => (this.documentStoreVersions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDocumentStoreVersions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDocumentStoreVersion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDocumentStoreVersions(): void {
    this.eventSubscriber = this.eventManager.subscribe('documentStoreVersionListModification', () => this.loadAll());
  }

  delete(documentStoreVersion: IDocumentStoreVersion): void {
    const modalRef = this.modalService.open(DocumentStoreVersionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.documentStoreVersion = documentStoreVersion;
  }
}
