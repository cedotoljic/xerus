import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDocumentStoreVersion, DocumentStoreVersion } from 'app/shared/model/document-store-version.model';
import { DocumentStoreVersionService } from './document-store-version.service';
import { DocumentStoreVersionComponent } from './document-store-version.component';
import { DocumentStoreVersionDetailComponent } from './document-store-version-detail.component';
import { DocumentStoreVersionUpdateComponent } from './document-store-version-update.component';

@Injectable({ providedIn: 'root' })
export class DocumentStoreVersionResolve implements Resolve<IDocumentStoreVersion> {
  constructor(private service: DocumentStoreVersionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDocumentStoreVersion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((documentStoreVersion: HttpResponse<DocumentStoreVersion>) => {
          if (documentStoreVersion.body) {
            return of(documentStoreVersion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DocumentStoreVersion());
  }
}

export const documentStoreVersionRoute: Routes = [
  {
    path: '',
    component: DocumentStoreVersionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DocumentStoreVersionDetailComponent,
    resolve: {
      documentStoreVersion: DocumentStoreVersionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DocumentStoreVersionUpdateComponent,
    resolve: {
      documentStoreVersion: DocumentStoreVersionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DocumentStoreVersionUpdateComponent,
    resolve: {
      documentStoreVersion: DocumentStoreVersionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'xerusApp.documentStoreVersion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
