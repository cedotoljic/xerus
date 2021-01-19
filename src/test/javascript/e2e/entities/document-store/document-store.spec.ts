import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentStoreComponentsPage, DocumentStoreDeleteDialog, DocumentStoreUpdatePage } from './document-store.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('DocumentStore e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentStoreComponentsPage: DocumentStoreComponentsPage;
  let documentStoreUpdatePage: DocumentStoreUpdatePage;
  let documentStoreDeleteDialog: DocumentStoreDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DocumentStores', async () => {
    await navBarPage.goToEntity('document-store');
    documentStoreComponentsPage = new DocumentStoreComponentsPage();
    await browser.wait(ec.visibilityOf(documentStoreComponentsPage.title), 5000);
    expect(await documentStoreComponentsPage.getTitle()).to.eq('xerusApp.documentStore.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(documentStoreComponentsPage.entities), ec.visibilityOf(documentStoreComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DocumentStore page', async () => {
    await documentStoreComponentsPage.clickOnCreateButton();
    documentStoreUpdatePage = new DocumentStoreUpdatePage();
    expect(await documentStoreUpdatePage.getPageTitle()).to.eq('xerusApp.documentStore.home.createOrEditLabel');
    await documentStoreUpdatePage.cancel();
  });

  it('should create and save DocumentStores', async () => {
    const nbButtonsBeforeCreate = await documentStoreComponentsPage.countDeleteButtons();

    await documentStoreComponentsPage.clickOnCreateButton();

    await promise.all([
      documentStoreUpdatePage.setNameInput('name'),
      documentStoreUpdatePage.setExtensionInput('extension'),
      documentStoreUpdatePage.setContentTypeInput('contentType'),
      documentStoreUpdatePage.setSizeInput('5'),
      documentStoreUpdatePage.setDocInput(absolutePath),
      documentStoreUpdatePage.statusSelectLastOption(),
      documentStoreUpdatePage.setOwnerInput('owner'),
      documentStoreUpdatePage.setResponsibleInput('responsible'),
      documentStoreUpdatePage.parentSelectLastOption(),
    ]);

    expect(await documentStoreUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await documentStoreUpdatePage.getExtensionInput()).to.eq('extension', 'Expected Extension value to be equals to extension');
    expect(await documentStoreUpdatePage.getContentTypeInput()).to.eq(
      'contentType',
      'Expected ContentType value to be equals to contentType'
    );
    expect(await documentStoreUpdatePage.getSizeInput()).to.eq('5', 'Expected size value to be equals to 5');
    expect(await documentStoreUpdatePage.getDocInput()).to.endsWith(
      fileNameToUpload,
      'Expected Doc value to be end with ' + fileNameToUpload
    );
    const selectedIsFolder = documentStoreUpdatePage.getIsFolderInput();
    if (await selectedIsFolder.isSelected()) {
      await documentStoreUpdatePage.getIsFolderInput().click();
      expect(await documentStoreUpdatePage.getIsFolderInput().isSelected(), 'Expected isFolder not to be selected').to.be.false;
    } else {
      await documentStoreUpdatePage.getIsFolderInput().click();
      expect(await documentStoreUpdatePage.getIsFolderInput().isSelected(), 'Expected isFolder to be selected').to.be.true;
    }
    expect(await documentStoreUpdatePage.getOwnerInput()).to.eq('owner', 'Expected Owner value to be equals to owner');
    expect(await documentStoreUpdatePage.getResponsibleInput()).to.eq(
      'responsible',
      'Expected Responsible value to be equals to responsible'
    );

    await documentStoreUpdatePage.save();
    expect(await documentStoreUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await documentStoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DocumentStore', async () => {
    const nbButtonsBeforeDelete = await documentStoreComponentsPage.countDeleteButtons();
    await documentStoreComponentsPage.clickOnLastDeleteButton();

    documentStoreDeleteDialog = new DocumentStoreDeleteDialog();
    expect(await documentStoreDeleteDialog.getDialogTitle()).to.eq('xerusApp.documentStore.delete.question');
    await documentStoreDeleteDialog.clickOnConfirmButton();

    expect(await documentStoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
