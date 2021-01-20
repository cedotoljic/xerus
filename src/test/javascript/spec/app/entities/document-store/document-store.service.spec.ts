import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DocumentStoreService } from 'app/entities/document-store/document-store.service';
import { IDocumentStore, DocumentStore } from 'app/shared/model/document-store.model';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

describe('Service Tests', () => {
  describe('DocumentStore Service', () => {
    let injector: TestBed;
    let service: DocumentStoreService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocumentStore;
    let expectedResult: IDocumentStore | IDocumentStore[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocumentStoreService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DocumentStore(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'image/png',
        'AAAAAAA',
        DocumentStatus.NEW,
        false,
        false,
        0,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DocumentStore', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DocumentStore()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DocumentStore', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            extension: 'BBBBBB',
            contentType: 'BBBBBB',
            size: 1,
            doc: 'BBBBBB',
            status: 'BBBBBB',
            isFolder: true,
            versionControlled: true,
            version: 1,
            owner: 'BBBBBB',
            responsible: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DocumentStore', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            extension: 'BBBBBB',
            contentType: 'BBBBBB',
            size: 1,
            doc: 'BBBBBB',
            status: 'BBBBBB',
            isFolder: true,
            versionControlled: true,
            version: 1,
            owner: 'BBBBBB',
            responsible: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DocumentStore', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
