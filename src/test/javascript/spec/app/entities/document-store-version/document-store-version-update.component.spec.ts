import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { XerusTestModule } from '../../../test.module';
import { DocumentStoreVersionUpdateComponent } from 'app/entities/document-store-version/document-store-version-update.component';
import { DocumentStoreVersionService } from 'app/entities/document-store-version/document-store-version.service';
import { DocumentStoreVersion } from 'app/shared/model/document-store-version.model';

describe('Component Tests', () => {
  describe('DocumentStoreVersion Management Update Component', () => {
    let comp: DocumentStoreVersionUpdateComponent;
    let fixture: ComponentFixture<DocumentStoreVersionUpdateComponent>;
    let service: DocumentStoreVersionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreVersionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DocumentStoreVersionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DocumentStoreVersionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentStoreVersionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DocumentStoreVersion(123);
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
        const entity = new DocumentStoreVersion();
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
