package cucumber.test;

import com.api.RandomData;
import com.api.Reporting;
import com.api.Selenium;
import com.browser.PardotBrowser;
import com.pardot.dashboard.PardotDashboard;
import com.pardot.login.PardotLogin;
import com.pardot.marketing.emails.PardotEmails;
import com.pardot.marketing.emails.emaileditor.building.PardotEmailBuilding;
import com.pardot.marketing.emails.emaileditor.sending.PardotEmailSending;
import com.pardot.marketing.emails.emailinformation.PardotBasicEmailInformation;
import com.pardot.marketing.emails.emailinformation.selectcampaign.PardotSelectCampaign;
import com.pardot.prospects.prospect.overview.PardotProspect;
import com.pardot.prospects.prospect.lists.PardotProspectLists;
import com.pardot.prospects.list.PardotProspects;
import com.pardot.prospects.createprospect.PardotCreateProspect;
import com.pardot.marketing.segmentation.lists.list.PardotSegmentationList;
import com.pardot.marketing.segmentation.lists.listinformation.PardotListInformation;
import com.pardot.marketing.segmentation.lists.listinformation.selectfolder.PardotSelectFolder;
import com.pardot.marketing.segmentation.lists.PardotSegmentationLists;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;

public class TestSteps {
    private String pardotUsername = "pardot.applicant@pardot.com";
    private String pardotPassword = "Applicant2012";
    private String pardotUrl = "https://pi.pardot.com";
    private Selenium selenium;

    private static Reporting reporting;

    private String folder;
    private String list;
    private String previousList;

    private RandomData random;

    private PardotBrowser browser;
    private PardotLogin login;
    private PardotDashboard dashboard;
    private PardotSegmentationLists segmentationLists;
    private PardotListInformation segmentationListInformation;
    private PardotSelectFolder selectFolder;
    private PardotSegmentationList segmentationList;
    private PardotProspects prospects;
    private PardotCreateProspect createProspect;
    private PardotProspect prospect;
    private PardotProspectLists prospectLists;
    private PardotEmails emails;
    private PardotBasicEmailInformation basicEmailInformation;
    private PardotSelectCampaign selectCampaign;
    private PardotEmailBuilding emailBuilding;
    private PardotEmailSending emailSending;

    public TestSteps() {
        reporting = new Reporting();

        random = new RandomData();

        browser = new PardotBrowser(reporting);
        login = new PardotLogin(reporting);
        dashboard = new PardotDashboard(reporting);
        segmentationLists = new PardotSegmentationLists(reporting);
        segmentationListInformation = new PardotListInformation(reporting);
        selectFolder = new PardotSelectFolder(reporting);
        segmentationList = new PardotSegmentationList(reporting);
        prospects = new PardotProspects(reporting);
        createProspect = new PardotCreateProspect(reporting);
        prospect = new PardotProspect(reporting);
        prospectLists = new PardotProspectLists(reporting);
        emails = new PardotEmails(reporting);
        basicEmailInformation = new PardotBasicEmailInformation(reporting);
        selectCampaign = new PardotSelectCampaign(reporting);
        emailBuilding = new PardotEmailBuilding(reporting);
        emailSending = new PardotEmailSending(reporting);
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        reporting.startTest(scenario.getName(), scenario.getName());

        selenium = null;

        previousList = null;

        selenium = browser.startBrowser(pardotUrl);
        login.isLogInPageLoaded(selenium);
        login.loginPardot(selenium, pardotUsername, pardotPassword);
    }

    @After
    public void afterScenario () throws InterruptedException, IOException {
        dashboard.signOut(selenium);
        login.isLogInPageLoaded(selenium);

        browser.stopBrowser(selenium);

        reporting.endTest();
        reporting.flush();

        //reporting.close();        //need global After to close this after all tests have run, but doesn't exist in cucumber yet
    }

    @Given("^I am logged into Pardot$")
    public void isDashboardPageExist() {
        dashboard.isDashboardPageLoaded(selenium);
    }

    @When("^I add the Segmentation List name \"([^\"]*)\" with a \"([^\"]*)\" folder$")
    public void createSegmentationList(String listName, String folderName) throws InterruptedException {
        dashboard.clickMarketingSegmentationLists(selenium);
        segmentationLists.isSegmentationListsPageLoaded(selenium);

        segmentationLists.clickAddListButton(selenium);
        segmentationListInformation.isListInformationModalLoaded(selenium);
        segmentationListInformation.isListInformationModalNotPopulated(selenium);

        if (listName.toUpperCase().equals("RANDOM")) {
            list = random.getRandomStringAlpha(20);
        } else if (listName.toUpperCase().equals("PREVIOUS")) {
            list = previousList;
        } else if (listName.toUpperCase().equals("DUPLICATE")) {

        } else {
            list = listName;
        }
        segmentationListInformation.createList(selenium, list);

        segmentationListInformation.clickChooseFolderButton(selenium);
        selectFolder.isSelectFolderModalLoaded(selenium);

        if (folderName.toUpperCase().equals("NEW")) {
            folder = random.getRandomStringAlpha(10);
            selectFolder.clickCreateFolderButton(selenium);
            selectFolder.addNewFolder(selenium, folder);
        } else if (!folderName.toUpperCase().equals("DUPLICATE")) {
            folder = folderName;
        }

        selectFolder.clickFolder(selenium, folder);
        selectFolder.clickChooseSelectedButton(selenium);

        segmentationListInformation.isListInformationModalLoaded(selenium);
        segmentationListInformation.isFolderSelected(selenium, folder);

        segmentationListInformation.saveList(selenium);
    }

    @When("^I edit the Segmentation List name \"([^\"]*)\" with a \"([^\"]*)\" folder$")
    public void editwSegmentationList(String listName, String folderName) throws InterruptedException {
        dashboard.clickMarketingSegmentationLists(selenium);
        segmentationLists.isSegmentationListsPageLoaded(selenium);

        segmentationLists.clickList(selenium, list);
        segmentationList.isListPageLoaded(selenium, list);
        segmentationList.clickEditListLink(selenium);
        segmentationListInformation.isListInformationModalLoaded(selenium);
        segmentationListInformation.isListInformationModalPopulated(selenium, list, folder);

        if (listName.toUpperCase().equals("RANDOM")) {
            previousList = list;
            list = random.getRandomStringAlpha(20);
        } else if (listName.toUpperCase().equals("PREVIOUS")) {
            list = previousList;
        } else if (listName.toUpperCase().equals("DUPLICATE")) {

        } else {
            list = listName;
        }
        segmentationListInformation.createList(selenium, list);

        segmentationListInformation.clickChooseFolderButton(selenium);
        selectFolder.isSelectFolderModalLoaded(selenium);

        if (folderName.toUpperCase().equals("NEW")) {
            folder = random.getRandomStringAlpha(10);
            selectFolder.clickCreateFolderButton(selenium);
            selectFolder.addNewFolder(selenium, folder);
        } else if (!folderName.toUpperCase().equals("DUPLICATE")) {
            folder = folderName;
        }

        selectFolder.clickFolder(selenium, folder);
        selectFolder.clickChooseSelectedButton(selenium);

        segmentationListInformation.isListInformationModalLoaded(selenium);
        segmentationListInformation.isFolderSelected(selenium, folder);

        segmentationListInformation.saveList(selenium);
    }

    @Then("^the Segmentation List is created$")
    public void isSegmentationListCreated() throws InterruptedException {
        segmentationList.isListPageLoaded(selenium, list);
        dashboard.clickMarketingSegmentationLists(selenium);

        segmentationLists.isSegmentationListsPageLoaded(selenium);
        segmentationLists.isListExist(selenium, list);
    }

    @Then("^the original Segmentation List does not Exist$")
    public void isOriginalSegmentationListRemoved() throws InterruptedException {
        segmentationLists.isListNotExist(selenium, previousList);
    }

    @Then("^the Segmentation List is flagged as Duplicate$")
    public void isSegmentationListDuplicate() throws InterruptedException {
        segmentationListInformation.isListInformationDuplicateNameErrorDisplayed(selenium);

        //close modal
        segmentationListInformation.cancelList(selenium);
    }



}
