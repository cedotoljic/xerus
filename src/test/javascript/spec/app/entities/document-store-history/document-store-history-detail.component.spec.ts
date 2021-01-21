import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { XerusTestModule } from '../../../test.module';
import { DocumentStoreHistoryDetailComponent } from 'app/entities/document-store-history/document-store-history-detail.component';
import { DocumentStoreHistory } from 'app/shared/model/document-store-history.model';

describe('Component Tests', () => {
  describe('DocumentStoreHistory Management Detail Component', () => {
    let comp: DocumentStoreHistoryDetailComponent;
    let fixture: ComponentFixture<DocumentStoreHistoryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ documentStoreHistory: new DocumentStoreHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DocumentStoreHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentStoreHistoryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load documentStoreHistory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.documentStoreHistory).toEqual(jasmine.objectContaining({ id: 123 }));
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
