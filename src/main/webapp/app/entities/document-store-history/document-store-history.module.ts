import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XerusSharedModule } from 'app/shared/shared.module';
import { DocumentStoreHistoryComponent } from './document-store-history.component';
import { DocumentStoreHistoryDetailComponent } from './document-store-history-detail.component';
import { DocumentStoreHistoryUpdateComponent } from './document-store-history-update.component';
import { DocumentStoreHistoryDeleteDialogComponent } from './document-store-history-delete-dialog.component';
import { documentStoreHistoryRoute } from './document-store-history.route';

@NgModule({
  imports: [XerusSharedModule, RouterModule.forChild(documentStoreHistoryRoute)],
  declarations: [
    DocumentStoreHistoryComponent,
    DocumentStoreHistoryDetailComponent,
    DocumentStoreHistoryUpdateComponent,
    DocumentStoreHistoryDeleteDialogComponent,
  ],
  entryComponents: [DocumentStoreHistoryDeleteDialogComponent],
})
export class XerusDocumentStoreHistoryModule {}
