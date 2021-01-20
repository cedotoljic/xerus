import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from './document-store.service';

@Component({
  templateUrl: './document-store-delete-dialog.component.html',
})
export class DocumentStoreDeleteDialogComponent {
  documentStore?: IDocumentStore;

  constructor(
    protected documentStoreService: DocumentStoreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentStoreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentStoreListModification');
      this.activeModal.close();
    });
  }
}
