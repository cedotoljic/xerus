import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumentStore } from 'app/shared/model/document-store.model';

@Component({
  selector: 'jhi-document-store-detail',
  templateUrl: './document-store-detail.component.html',
})
export class DocumentStoreDetailComponent implements OnInit {
  documentStore: IDocumentStore | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStore }) => (this.documentStore = documentStore));
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
