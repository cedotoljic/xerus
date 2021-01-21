import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { XerusTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { DocumentStoreHistoryDeleteDialogComponent } from 'app/entities/document-store-history/document-store-history-delete-dialog.component';
import { DocumentStoreHistoryService } from 'app/entities/document-store-history/document-store-history.service';

describe('Component Tests', () => {
  describe('DocumentStoreHistory Management Delete Component', () => {
    let comp: DocumentStoreHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<DocumentStoreHistoryDeleteDialogComponent>;
    let service: DocumentStoreHistoryService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [XerusTestModule],
        declarations: [DocumentStoreHistoryDeleteDialogComponent],
      })
        .overrideTemplate(DocumentStoreHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DocumentStoreHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DocumentStoreHistoryService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
