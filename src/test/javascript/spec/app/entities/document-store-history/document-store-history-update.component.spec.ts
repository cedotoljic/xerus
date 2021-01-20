import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { XerusTestModule } from '../../../test.module';
import { DocumentStoreHistoryUpdateComponent } from 'app/entities/document-store-history/document-store-history-update.component';
import { DocumentStoreHistoryService } from 'app/entities/document-store-history/document-store-history.service';
import { DocumentStoreHistory } from 'app/shared/model/document-store-history.model';

describe('Component Tests', () => {
  describe('DocumentStoreHistory Management Update Component', () => {
    let comp: DocumentStoreHistoryUpdateComponent;
    let fixture: ComponentFixture<DocumentStoreHistoryUpdateComponent>;
    let service: DocumentStoreHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreHistoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DocumentStoreHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentStoreHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentStoreHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentStoreHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentStoreHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
