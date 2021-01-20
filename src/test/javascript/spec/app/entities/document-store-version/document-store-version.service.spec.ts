import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DocumentStoreVersionService } from 'app/entities/document-store-version/document-store-version.service';
import { IDocumentStoreVersion, DocumentStoreVersion } from 'app/shared/model/document-store-version.model';
import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

describe('Service Tests', () => {
  describe('DocumentStoreVersion Service', () => {
    let injector: TestBed;
    let service: DocumentStoreVersionService;
    let httpMock: HttpTestingController;
    let elemDefault: IDocumentStoreVersion;
    let expectedResult: IDocumentStoreVersion | IDocumentStoreVersion[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DocumentStoreVersionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new DocumentStoreVersion(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'image/png',
        'AAAAAAA',
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

      it('should create a DocumentStoreVersion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new DocumentStoreVersion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DocumentStoreVersion', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            extension: 'BBBBBB',
            contentType: 'BBBBBB',
            size: 1,
            doc: 'BBBBBB',
            thumbnail: 'BBBBBB',
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

      it('should return a list of DocumentStoreVersion', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            extension: 'BBBBBB',
            contentType: 'BBBBBB',
            size: 1,
            doc: 'BBBBBB',
            thumbnail: 'BBBBBB',
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

      it('should delete a DocumentStoreVersion', () => {
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
