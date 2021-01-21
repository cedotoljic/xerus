import { element, by, ElementFinder } from 'protractor';

export class DocumentStoreComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-document-store div table .btn-danger'));
  title = element.all(by.css('jhi-document-store div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DocumentStoreUpdatePage {
  pageTitle = element(by.id('jhi-document-store-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  extensionInput = element(by.id('field_extension'));
  sizeInput = element(by.id('field_size'));
  docInput = element(by.id('file_doc'));
  thumbnailInput = element(by.id('file_thumbnail'));
  statusSelect = element(by.id('field_status'));
  isFolderInput = element(by.id('field_isFolder'));
  versionControlledInput = element(by.id('field_versionControlled'));
  versionInput = element(by.id('field_version'));
  ownerInput = element(by.id('field_owner'));
  responsibleInput = element(by.id('field_responsible'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setExtensionInput(extension: string): Promise<void> {
    await this.extensionInput.sendKeys(extension);
  }

  async getExtensionInput(): Promise<string> {
    return await this.extensionInput.getAttribute('value');
  }

  async setSizeInput(size: string): Promise<void> {
    await this.sizeInput.sendKeys(size);
  }

  async getSizeInput(): Promise<string> {
    return await this.sizeInput.getAttribute('value');
  }

  async setDocInput(doc: string): Promise<void> {
    await this.docInput.sendKeys(doc);
  }

  async getDocInput(): Promise<string> {
    return await this.docInput.getAttribute('value');
  }

  async setThumbnailInput(thumbnail: string): Promise<void> {
    await this.thumbnailInput.sendKeys(thumbnail);
  }

  async getThumbnailInput(): Promise<string> {
    return await this.thumbnailInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  getIsFolderInput(): ElementFinder {
    return this.isFolderInput;
  }

  getVersionControlledInput(): ElementFinder {
    return this.versionControlledInput;
  }

  async setVersionInput(version: string): Promise<void> {
    await this.versionInput.sendKeys(version);
  }

  async getVersionInput(): Promise<string> {
    return await this.versionInput.getAttribute('value');
  }

  async setOwnerInput(owner: string): Promise<void> {
    await this.ownerInput.sendKeys(owner);
  }

  async getOwnerInput(): Promise<string> {
    return await this.ownerInput.getAttribute('value');
  }

  async setResponsibleInput(responsible: string): Promise<void> {
    await this.responsibleInput.sendKeys(responsible);
  }

  async getResponsibleInput(): Promise<string> {
    return await this.responsibleInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DocumentStoreDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-documentStore-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-documentStore'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
