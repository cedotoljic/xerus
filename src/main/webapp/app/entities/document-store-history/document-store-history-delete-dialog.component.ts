import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentStoreHistory } from 'app/shared/model/document-store-history.model';
import { DocumentStoreHistoryService } from './document-store-history.service';

@Component({
  templateUrl: './document-store-history-delete-dialog.component.html',
})
export class DocumentStoreHistoryDeleteDialogComponent {
  documentStoreHistory?: IDocumentStoreHistory;

  constructor(
    protected documentStoreHistoryService: DocumentStoreHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.documentStoreHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('documentStoreHistoryListModification');
      this.activeModal.close();
    });
  }
}
