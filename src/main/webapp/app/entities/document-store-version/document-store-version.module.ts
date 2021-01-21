import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { XerusSharedModule } from 'app/shared/shared.module';
import { DocumentStoreVersionComponent } from './document-store-version.component';
import { DocumentStoreVersionDetailComponent } from './document-store-version-detail.component';
import { DocumentStoreVersionUpdateComponent } from './document-store-version-update.component';
import { DocumentStoreVersionDeleteDialogComponent } from './document-store-version-delete-dialog.component';
import { documentStoreVersionRoute } from './document-store-version.route';

@NgModule({
  imports: [XerusSharedModule, RouterModule.forChild(documentStoreVersionRoute)],
  declarations: [
    DocumentStoreVersionComponent,
    DocumentStoreVersionDetailComponent,
    DocumentStoreVersionUpdateComponent,
    DocumentStoreVersionDeleteDialogComponent,
  ],
  entryComponents: [DocumentStoreVersionDeleteDialogComponent],
})
export class XerusDocumentStoreVersionModule {}
