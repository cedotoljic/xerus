import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { XerusTestModule } from '../../../test.module';
import { DocumentStoreVersionDetailComponent } from 'app/entities/document-store-version/document-store-version-detail.component';
import { DocumentStoreVersion } from 'app/shared/model/document-store-version.model';

describe('Component Tests', () => {
  describe('DocumentStoreVersion Management Detail Component', () => {
    let comp: DocumentStoreVersionDetailComponent;
    let fixture: ComponentFixture<DocumentStoreVersionDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ documentStoreVersion: new DocumentStoreVersion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreVersionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DocumentStoreVersionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentStoreVersionDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load documentStoreVersion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentStoreVersion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
