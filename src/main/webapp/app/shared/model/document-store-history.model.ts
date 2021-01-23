import { DocumentStatus } from 'app/shared/model/enumerations/document-status.model';

export interface IDocumentStoreHistory {
  id?: number;
  name?: string;
  extension?: string;
  size?: number;
  docContentType?: string;
  doc?: any;
  thumbnailContentType?: string;
  thumbnail?: any;
  status?: DocumentStatus;
  isFolder?: boolean;
  versionControlled?: boolean;
  version?: number;
  owner?: string;
  responsible?: string;
  documentId?: number;
}

export class DocumentStoreHistory implements IDocumentStoreHistory {
  constructor(
    public id?: number,
    public name?: string,
    public extension?: string,
    public size?: number,
    public docContentType?: string,
    public doc?: any,
    public thumbnailContentType?: string,
    public thumbnail?: any,
    public status?: DocumentStatus,
    public isFolder?: boolean,
    public versionControlled?: boolean,
    public version?: number,
    public owner?: string,
    public responsible?: string,
    public documentId?: number
  ) {
    this.isFolder = this.isFolder || false;
    this.versionControlled = this.versionControlled || false;
  }
}
