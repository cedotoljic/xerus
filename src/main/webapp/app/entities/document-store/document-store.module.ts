import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XerusSharedModule } from 'app/shared/shared.module';
import { DocumentStoreComponent } from './document-store.component';
import { DocumentStoreDetailComponent } from './document-store-detail.component';
import { DocumentStoreUpdateComponent } from './document-store-update.component';
import { DocumentStoreDeleteDialogComponent } from './document-store-delete-dialog.component';
import { documentStoreRoute } from './document-store.route';

@NgModule({
  imports: [XerusSharedModule, RouterModule.forChild(documentStoreRoute)],
  declarations: [DocumentStoreComponent, DocumentStoreDetailComponent, DocumentStoreUpdateComponent, DocumentStoreDeleteDialogComponent],
  entryComponents: [DocumentStoreDeleteDialogComponent],
})
export class XerusDocumentStoreModule {}
