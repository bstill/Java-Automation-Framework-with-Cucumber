package cucumber.test;

import com.api.RandomData;
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

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestSteps {
    private String pardotUsername = "pardot.applicant@pardot.com";
    private String pardotPassword = "Applicant2012";
    private String pardotUrl = "https://pi.pardot.com";
    private Selenium selenium;

    private String folder;
    private String list;

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
        random = new RandomData();

        browser = new PardotBrowser();
        login = new PardotLogin();
        dashboard = new PardotDashboard();
        segmentationLists = new PardotSegmentationLists();
        segmentationListInformation = new PardotListInformation();
        selectFolder = new PardotSelectFolder();
        segmentationList = new PardotSegmentationList();
        prospects = new PardotProspects();
        createProspect = new PardotCreateProspect();
        prospect = new PardotProspect();
        prospectLists = new PardotProspectLists();
        emails = new PardotEmails();
        basicEmailInformation = new PardotBasicEmailInformation();
        selectCampaign = new PardotSelectCampaign();
        emailBuilding = new PardotEmailBuilding();
        emailSending = new PardotEmailSending();
    }

    @Before
    public void beforeScenario() {
        selenium = null;

        selenium = browser.startBrowser(pardotUrl);
        login.isLogInPageLoaded(selenium);
        login.loginPardot(selenium, pardotUsername, pardotPassword);
    }

    @After
    public void afterScenario () throws InterruptedException {
        dashboard.signOut(selenium);
        login.isLogInPageLoaded(selenium);

        browser.stopBrowser(selenium);
    }

    @Given("^I am logged into Pardot$")
    public void isDashboardPageExist() {
        dashboard.isDashboardPageLoaded(selenium);
    }

    @When("^I navigate to the Segmentation Lists page$")
    public void goSegmentationListsPage() throws InterruptedException {
        dashboard.clickMarketingSegmentationLists(selenium);
        segmentationLists.isSegmentationListsPageLoaded(selenium);
    }

    @And("^I open the Add List modal for a new list$")
    public void openAddSegmentationListModalNew() {
        segmentationLists.clickAddListButton(selenium);
        segmentationListInformation.isListInformationModalLoaded(selenium);
        segmentationListInformation.isListInformationModalNotPopulated(selenium);
    }

    @And("^I add the Segmentation List name \"([^\"]*)\" with a \"([^\"]*)\" folder$")
    public void createNewSegmentationList(String listName, String folderStatus) {
        if (listName.toUpperCase().equals("RANDOM")) {
            list = random.getRandomStringAlpha(20);
        }
        segmentationListInformation.createList(selenium, list);

        segmentationListInformation.clickChooseFolderButton(selenium);
        selectFolder.isSelectFolderModalLoaded(selenium);

        if (folderStatus.toUpperCase().equals("NEW")) {
            folder = random.getRandomStringAlpha(10);
            selectFolder.clickCreateFolderButton(selenium);
            selectFolder.addNewFolder(selenium, folder);
        }

        selectFolder.clickFolder(selenium, folder);
        selectFolder.clickChooseSelectedButton(selenium);

        segmentationListInformation.isListInformationModalLoaded(selenium);
        segmentationListInformation.isFolderSelected(selenium, folder);
    }

    @And("^I save the Segmentation List$")
    public void saveSegmentationList() {
        segmentationListInformation.saveList(selenium);
    }

    @Then("^the Segmentation List is created$")
    public void isSegmentationListExist() throws InterruptedException {
        segmentationList.isListPageLoaded(selenium, list);
        dashboard.clickMarketingSegmentationLists(selenium);

        segmentationLists.isSegmentationListsPageLoaded(selenium);
        segmentationLists.isListExist(selenium, list);
    }








}
