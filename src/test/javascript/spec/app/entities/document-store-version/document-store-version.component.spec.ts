import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { XerusTestModule } from '../../../test.module';
import { DocumentStoreVersionComponent } from 'app/entities/document-store-version/document-store-version.component';
import { DocumentStoreVersionService } from 'app/entities/document-store-version/document-store-version.service';
import { DocumentStoreVersion } from 'app/shared/model/document-store-version.model';

describe('Component Tests', () => {
  describe('DocumentStoreVersion Management Component', () => {
    let comp: DocumentStoreVersionComponent;
    let fixture: ComponentFixture<DocumentStoreVersionComponent>;
    let service: DocumentStoreVersionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreVersionComponent],
      })
        .overrideTemplate(DocumentStoreVersionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentStoreVersionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentStoreVersionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DocumentStoreVersion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.documentStoreVersions && comp.documentStoreVersions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
