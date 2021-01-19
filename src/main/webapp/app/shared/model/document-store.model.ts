import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface IDocumentStore {
  id?: number;
  name?: string;
  extension?: string;
  contentType?: string;
  size?: number;
  docContentType?: string;
  doc?: any;
  status?: DocumentStatus;
  isFolder?: boolean;
  owner?: string;
  responsible?: string;
  parent?: IDocumentStore;
}

export class DocumentStore implements IDocumentStore {
  constructor(
    public id?: number,
    public name?: string,
    public extension?: string,
    public contentType?: string,
    public size?: number,
    public docContentType?: string,
    public doc?: any,
    public status?: DocumentStatus,
    public isFolder?: boolean,
    public owner?: string,
    public responsible?: string,
    public parent?: IDocumentStore
  ) {
    this.isFolder = this.isFolder || false;
  }
}
