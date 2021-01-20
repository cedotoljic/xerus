import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DocumentStoreHistoryComponentsPage,
  DocumentStoreHistoryDeleteDialog,
  DocumentStoreHistoryUpdatePage,
} from './document-store-history.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('DocumentStoreHistory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentStoreHistoryComponentsPage: DocumentStoreHistoryComponentsPage;
  let documentStoreHistoryUpdatePage: DocumentStoreHistoryUpdatePage;
  let documentStoreHistoryDeleteDialog: DocumentStoreHistoryDeleteDialog;
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

  it('should load DocumentStoreHistories', async () => {
    await navBarPage.goToEntity('document-store-history');
    documentStoreHistoryComponentsPage = new DocumentStoreHistoryComponentsPage();
    await browser.wait(ec.visibilityOf(documentStoreHistoryComponentsPage.title), 5000);
    expect(await documentStoreHistoryComponentsPage.getTitle()).to.eq('xerusApp.documentStoreHistory.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(documentStoreHistoryComponentsPage.entities), ec.visibilityOf(documentStoreHistoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DocumentStoreHistory page', async () => {
    await documentStoreHistoryComponentsPage.clickOnCreateButton();
    documentStoreHistoryUpdatePage = new DocumentStoreHistoryUpdatePage();
    expect(await documentStoreHistoryUpdatePage.getPageTitle()).to.eq('xerusApp.documentStoreHistory.home.createOrEditLabel');
    await documentStoreHistoryUpdatePage.cancel();
  });

  it('should create and save DocumentStoreHistories', async () => {
    const nbButtonsBeforeCreate = await documentStoreHistoryComponentsPage.countDeleteButtons();

    await documentStoreHistoryComponentsPage.clickOnCreateButton();

    await promise.all([
      documentStoreHistoryUpdatePage.setNameInput('name'),
      documentStoreHistoryUpdatePage.setExtensionInput('extension'),
      documentStoreHistoryUpdatePage.setContentTypeInput('contentType'),
      documentStoreHistoryUpdatePage.setSizeInput('5'),
      documentStoreHistoryUpdatePage.setDocInput(absolutePath),
      documentStoreHistoryUpdatePage.setThumbnailInput(absolutePath),
      documentStoreHistoryUpdatePage.statusSelectLastOption(),
      documentStoreHistoryUpdatePage.setVersionInput('5'),
      documentStoreHistoryUpdatePage.setOwnerInput('owner'),
      documentStoreHistoryUpdatePage.setResponsibleInput('responsible'),
      documentStoreHistoryUpdatePage.documentSelectLastOption(),
    ]);

    expect(await documentStoreHistoryUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await documentStoreHistoryUpdatePage.getExtensionInput()).to.eq(
      'extension',
      'Expected Extension value to be equals to extension'
    );
    expect(await documentStoreHistoryUpdatePage.getContentTypeInput()).to.eq(
      'contentType',
      'Expected ContentType value to be equals to contentType'
    );
    expect(await documentStoreHistoryUpdatePage.getSizeInput()).to.eq('5', 'Expected size value to be equals to 5');
    expect(await documentStoreHistoryUpdatePage.getDocInput()).to.endsWith(
      fileNameToUpload,
      'Expected Doc value to be end with ' + fileNameToUpload
    );
    expect(await documentStoreHistoryUpdatePage.getThumbnailInput()).to.endsWith(
      fileNameToUpload,
      'Expected Thumbnail value to be end with ' + fileNameToUpload
    );
    const selectedIsFolder = documentStoreHistoryUpdatePage.getIsFolderInput();
    if (await selectedIsFolder.isSelected()) {
      await documentStoreHistoryUpdatePage.getIsFolderInput().click();
      expect(await documentStoreHistoryUpdatePage.getIsFolderInput().isSelected(), 'Expected isFolder not to be selected').to.be.false;
    } else {
      await documentStoreHistoryUpdatePage.getIsFolderInput().click();
      expect(await documentStoreHistoryUpdatePage.getIsFolderInput().isSelected(), 'Expected isFolder to be selected').to.be.true;
    }
    const selectedVersionControlled = documentStoreHistoryUpdatePage.getVersionControlledInput();
    if (await selectedVersionControlled.isSelected()) {
      await documentStoreHistoryUpdatePage.getVersionControlledInput().click();
      expect(await documentStoreHistoryUpdatePage.getVersionControlledInput().isSelected(), 'Expected versionControlled not to be selected')
        .to.be.false;
    } else {
      await documentStoreHistoryUpdatePage.getVersionControlledInput().click();
      expect(await documentStoreHistoryUpdatePage.getVersionControlledInput().isSelected(), 'Expected versionControlled to be selected').to
        .be.true;
    }
    expect(await documentStoreHistoryUpdatePage.getVersionInput()).to.eq('5', 'Expected version value to be equals to 5');
    expect(await documentStoreHistoryUpdatePage.getOwnerInput()).to.eq('owner', 'Expected Owner value to be equals to owner');
    expect(await documentStoreHistoryUpdatePage.getResponsibleInput()).to.eq(
      'responsible',
      'Expected Responsible value to be equals to responsible'
    );

    await documentStoreHistoryUpdatePage.save();
    expect(await documentStoreHistoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await documentStoreHistoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DocumentStoreHistory', async () => {
    const nbButtonsBeforeDelete = await documentStoreHistoryComponentsPage.countDeleteButtons();
    await documentStoreHistoryComponentsPage.clickOnLastDeleteButton();

    documentStoreHistoryDeleteDialog = new DocumentStoreHistoryDeleteDialog();
    expect(await documentStoreHistoryDeleteDialog.getDialogTitle()).to.eq('xerusApp.documentStoreHistory.delete.question');
    await documentStoreHistoryDeleteDialog.clickOnConfirmButton();

    expect(await documentStoreHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
