import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumentStoreHistory } from 'app/shared/model/document-store-history.model';

@Component({
  selector: 'jhi-document-store-history-detail',
  templateUrl: './document-store-history-detail.component.html',
})
export class DocumentStoreHistoryDetailComponent implements OnInit {
  documentStoreHistory: IDocumentStoreHistory | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStoreHistory }) => (this.documentStoreHistory = documentStoreHistory));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
