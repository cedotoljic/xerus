import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentStore, DocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStoreService } from './document-store.service';
import { DocumentStoreComponent } from './document-store.component';
import { DocumentStoreDetailComponent } from './document-store-detail.component';
import { DocumentStoreUpdateComponent } from './document-store-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentStoreResolve implements Resolve<IDocumentStore> {
  constructor(private service: DocumentStoreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentStore> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentStore: HttpResponse<DocumentStore>) => {
          if (documentStore.body) {
            return of(documentStore.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentStore());
  }
}

export const documentStoreRoute: Routes = [
  {
    path: '',
    component: DocumentStoreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStore.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentStoreDetailComponent,
    resolve: {
      documentStore: DocumentStoreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStore.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentStoreUpdateComponent,
    resolve: {
      documentStore: DocumentStoreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStore.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentStoreUpdateComponent,
    resolve: {
      documentStore: DocumentStoreResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStore.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
