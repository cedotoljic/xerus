import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumentStoreVersion } from 'app/shared/model/document-store-version.model';

@Component({
  selector: 'jhi-document-store-version-detail',
  templateUrl: './document-store-version-detail.component.html',
})
export class DocumentStoreVersionDetailComponent implements OnInit {
  documentStoreVersion: IDocumentStoreVersion | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ documentStoreVersion }) => (this.documentStoreVersion = documentStoreVersion));
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
