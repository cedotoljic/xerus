import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { XerusTestModule } from '../../../test.module';
import { DocumentStoreDetailComponent } from 'app/entities/document-store/document-store-detail.component';
import { DocumentStore } from 'app/shared/model/document-store.model';

describe('Component Tests', () => {
  describe('DocumentStore Management Detail Component', () => {
    let comp: DocumentStoreDetailComponent;
    let fixture: ComponentFixture<DocumentStoreDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ documentStore: new DocumentStore(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DocumentStoreDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentStoreDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load documentStore on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentStore).toEqual(jasmine.objectContaining({ id: 123 }));
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
