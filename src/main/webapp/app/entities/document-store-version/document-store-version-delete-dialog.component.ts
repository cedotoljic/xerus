import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentStoreVersion } from 'app/shared/model/document-store-version.model';
import { DocumentStoreVersionService } from './document-store-version.service';

@Component({
  templateUrl: './document-store-version-delete-dialog.component.html',
})
export class DocumentStoreVersionDeleteDialogComponent {
  documentStoreVersion?: IDocumentStoreVersion;

  constructor(
    protected documentStoreVersionService: DocumentStoreVersionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentStoreVersionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentStoreVersionListModification');
      this.activeModal.close();
    });
  }
}
