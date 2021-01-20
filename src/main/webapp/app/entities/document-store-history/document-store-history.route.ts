import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentStoreHistory, DocumentStoreHistory } from 'app/shared/model/document-store-history.model';
import { DocumentStoreHistoryService } from './document-store-history.service';
import { DocumentStoreHistoryComponent } from './document-store-history.component';
import { DocumentStoreHistoryDetailComponent } from './document-store-history-detail.component';
import { DocumentStoreHistoryUpdateComponent } from './document-store-history-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentStoreHistoryResolve implements Resolve<IDocumentStoreHistory> {
  constructor(private service: DocumentStoreHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentStoreHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentStoreHistory: HttpResponse<DocumentStoreHistory>) => {
          if (documentStoreHistory.body) {
            return of(documentStoreHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentStoreHistory());
  }
}

export const documentStoreHistoryRoute: Routes = [
  {
    path: '',
    component: DocumentStoreHistoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentStoreHistoryDetailComponent,
    resolve: {
      documentStoreHistory: DocumentStoreHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentStoreHistoryUpdateComponent,
    resolve: {
      documentStoreHistory: DocumentStoreHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentStoreHistoryUpdateComponent,
    resolve: {
      documentStoreHistory: DocumentStoreHistoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreHistory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
