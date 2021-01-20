import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document-store',
        loadChildren: () => import('./document-store/document-store.module').then(m => m.XerusDocumentStoreModule),
      },
      {
        path: 'document-store-history',
        loadChildren: () => import('./document-store-history/document-store-history.module').then(m => m.XerusDocumentStoreHistoryModule),
      },
      {
        path: 'document-store-version',
        loadChildren: () => import('./document-store-version/document-store-version.module').then(m => m.XerusDocumentStoreVersionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class XerusEntityModule {}
