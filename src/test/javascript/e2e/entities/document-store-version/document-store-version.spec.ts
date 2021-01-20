import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DocumentStoreVersionComponentsPage,
  DocumentStoreVersionDeleteDialog,
  DocumentStoreVersionUpdatePage,
} from './document-store-version.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('DocumentStoreVersion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let documentStoreVersionComponentsPage: DocumentStoreVersionComponentsPage;
  let documentStoreVersionUpdatePage: DocumentStoreVersionUpdatePage;
  let documentStoreVersionDeleteDialog: DocumentStoreVersionDeleteDialog;
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

  it('should load DocumentStoreVersions', async () => {
    await navBarPage.goToEntity('document-store-version');
    documentStoreVersionComponentsPage = new DocumentStoreVersionComponentsPage();
    await browser.wait(ec.visibilityOf(documentStoreVersionComponentsPage.title), 5000);
    expect(await documentStoreVersionComponentsPage.getTitle()).to.eq('xerusApp.documentStoreVersion.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(documentStoreVersionComponentsPage.entities), ec.visibilityOf(documentStoreVersionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DocumentStoreVersion page', async () => {
    await documentStoreVersionComponentsPage.clickOnCreateButton();
    documentStoreVersionUpdatePage = new DocumentStoreVersionUpdatePage();
    expect(await documentStoreVersionUpdatePage.getPageTitle()).to.eq('xerusApp.documentStoreVersion.home.createOrEditLabel');
    await documentStoreVersionUpdatePage.cancel();
  });

  it('should create and save DocumentStoreVersions', async () => {
    const nbButtonsBeforeCreate = await documentStoreVersionComponentsPage.countDeleteButtons();

    await documentStoreVersionComponentsPage.clickOnCreateButton();

    await promise.all([
      documentStoreVersionUpdatePage.setNameInput('name'),
      documentStoreVersionUpdatePage.setExtensionInput('extension'),
      documentStoreVersionUpdatePage.setContentTypeInput('contentType'),
      documentStoreVersionUpdatePage.setSizeInput('5'),
      documentStoreVersionUpdatePage.setDocInput(absolutePath),
      documentStoreVersionUpdatePage.statusSelectLastOption(),
      documentStoreVersionUpdatePage.setVersionInput('5'),
      documentStoreVersionUpdatePage.setOwnerInput('owner'),
      documentStoreVersionUpdatePage.setResponsibleInput('responsible'),
      documentStoreVersionUpdatePage.documentSelectLastOption(),
    ]);

    expect(await documentStoreVersionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await documentStoreVersionUpdatePage.getExtensionInput()).to.eq(
      'extension',
      'Expected Extension value to be equals to extension'
    );
    expect(await documentStoreVersionUpdatePage.getContentTypeInput()).to.eq(
      'contentType',
      'Expected ContentType value to be equals to contentType'
    );
    expect(await documentStoreVersionUpdatePage.getSizeInput()).to.eq('5', 'Expected size value to be equals to 5');
    expect(await documentStoreVersionUpdatePage.getDocInput()).to.endsWith(
      fileNameToUpload,
      'Expected Doc value to be end with ' + fileNameToUpload
    );
    const selectedIsFolder = documentStoreVersionUpdatePage.getIsFolderInput();
    if (await selectedIsFolder.isSelected()) {
      await documentStoreVersionUpdatePage.getIsFolderInput().click();
      expect(await documentStoreVersionUpdatePage.getIsFolderInput().isSelected(), 'Expected isFolder not to be selected').to.be.false;
    } else {
      await documentStoreVersionUpdatePage.getIsFolderInput().click();
      expect(await documentStoreVersionUpdatePage.getIsFolderInput().isSelected(), 'Expected isFolder to be selected').to.be.true;
    }
    const selectedVersionControlled = documentStoreVersionUpdatePage.getVersionControlledInput();
    if (await selectedVersionControlled.isSelected()) {
      await documentStoreVersionUpdatePage.getVersionControlledInput().click();
      expect(await documentStoreVersionUpdatePage.getVersionControlledInput().isSelected(), 'Expected versionControlled not to be selected')
        .to.be.false;
    } else {
      await documentStoreVersionUpdatePage.getVersionControlledInput().click();
      expect(await documentStoreVersionUpdatePage.getVersionControlledInput().isSelected(), 'Expected versionControlled to be selected').to
        .be.true;
    }
    expect(await documentStoreVersionUpdatePage.getVersionInput()).to.eq('5', 'Expected version value to be equals to 5');
    expect(await documentStoreVersionUpdatePage.getOwnerInput()).to.eq('owner', 'Expected Owner value to be equals to owner');
    expect(await documentStoreVersionUpdatePage.getResponsibleInput()).to.eq(
      'responsible',
      'Expected Responsible value to be equals to responsible'
    );

    await documentStoreVersionUpdatePage.save();
    expect(await documentStoreVersionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await documentStoreVersionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DocumentStoreVersion', async () => {
    const nbButtonsBeforeDelete = await documentStoreVersionComponentsPage.countDeleteButtons();
    await documentStoreVersionComponentsPage.clickOnLastDeleteButton();

    documentStoreVersionDeleteDialog = new DocumentStoreVersionDeleteDialog();
    expect(await documentStoreVersionDeleteDialog.getDialogTitle()).to.eq('xerusApp.documentStoreVersion.delete.question');
    await documentStoreVersionDeleteDialog.clickOnConfirmButton();

    expect(await documentStoreVersionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
