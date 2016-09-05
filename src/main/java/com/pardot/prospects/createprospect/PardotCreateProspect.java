package com.pardot.prospects.createprospect;

import com.api.Reporting;
import com.api.Selenium;
import com.api.RandomData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PardotCreateProspect {
    private Reporting reporting;

    private RandomData random = new RandomData();

    private String pageTitleText = "Prospects";
    private By createProspectPageSubTitle = By.xpath(".//*[@id='pr_form_update']/form/h3");
    private String pageSubTitleText = "Create Prospect";

    private By firstNameField = By.name("default_field_3361");
    private By lastNameField = By.name("default_field_3371");
    private By emailField = By.name("email");
    private By campaignDropdown = By.name("campaign_id");
    private By profileDropdown = By.name("profile_id");
    private By scoreField = By.name("score");
    private By listsToggleButton = By.id("toggle-inputs-lists-");
    private By listsDropdown = By.xpath(".//*[@id='pr_fields_lists_wrapper_']/div/div/div/div/a/div/b");
    private By listsSearchDiv = By.className("chzn-search");
    private By listsSearchResultsDropdown = By.className("chzn-results");
    private By createProspectCommitButton = By.xpath(".//*[@id='pr_form_update']/form/div[21]/input[3]");

    private By listsSelectedContainer = By.className("selected-lists");

    private By waitIndicator = By.id("indicator");

    public PardotCreateProspect(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isCreateProspectPageLoaded(Selenium selenium) {
        reporting.writeStep("Verify Create Prospect Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify Create Prospect Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }

        reporting.writeInfo("Verify Lists Page Sub Title is: " + pageSubTitleText);
        if (!selenium.getText(createProspectPageSubTitle).equals(pageSubTitleText)) {
            selenium.throwRuntimeException("Page Sub Title is Not: " + pageSubTitleText, true);
        } else {
            reporting.writePass("Page Sub Title Found");
        }
    }

    public void createProspect(Selenium selenium, String firstName, String lastName, String email, String score, String listName) {
        reporting.writeStep("Create a New Prospect");

        reporting.writeInfo("Enter First Name: " + firstName);
        selenium.clear(firstNameField);
        selenium.sendKeys(firstNameField, firstName);

        reporting.writeInfo("Enter Last Name: " + lastName);
        selenium.clear(lastNameField);
        selenium.sendKeys(lastNameField, lastName);

        reporting.writeInfo("Enter Email: " + email);
        selenium.clear(emailField);
        selenium.sendKeys(emailField, email);

        reporting.writeInfo("Select Random Campaign from Current Group");
        List<WebElement> elements = selenium.findElements(campaignDropdown, By.tagName("optgroup"));

        for(WebElement e : elements){
            if (selenium.getAttribute(e, "label").equals("Current")) {
                reporting.writeInfo("Found Current Group");
                List<WebElement> selectOptions = selenium.findElements(e, By.tagName("option"));

                reporting.writeInfo("Select Campaign");
                selenium.click(campaignDropdown);

                selenium.switchToWindow();
                selenium.doubleClick(selectOptions.get(random.getRandomNumber(selectOptions.size())));

                break;
            }
        }

        reporting.writeInfo("Select Random Profile");
        elements = selenium.findElements(profileDropdown, By.tagName("option"));

        reporting.writeInfo("Select Profile");
        selenium.click(profileDropdown);

        Integer rand;
        do {
            rand = random.getRandomNumber(elements.size());

            selenium.switchToWindow();

            selenium.doubleClick(elements.get(rand));
        } while (rand == 0);

        reporting.writeInfo("Enter Score: " + score);
        selenium.clear(scoreField);
        selenium.sendKeys(scoreField, score);

        reporting.writeInfo("Toggle List Selection Section");
        selenium.click(listsToggleButton);

        reporting.writeInfo("Open List Dropdown");
        selenium.click(listsDropdown);

        reporting.writeInfo("Search for List: " + listName);
        WebElement search = selenium.findChildElement(listsSearchDiv, By.tagName("input"));
        selenium.sendKeys(search, listName);

        reporting.writeInfo("Select List");
        selenium.doubleClick(listsSearchResultsDropdown);

        isProspectListExist(selenium, listName);
    }

    public void isProspectListExist(Selenium selenium, String listName) {
        reporting.writeStep("Check List Added");
        reporting.writeInfo("Verify List Added to Prospect: " + listName);
        List<WebElement> elements = selenium.findElements(listsSelectedContainer, By.tagName("li"));

        for(WebElement e : elements){
            if (selenium.getAttribute(e, "data-name").equals(listName)) {
                reporting.writePass("Found List");

                return;
            }
        }

        selenium.throwRuntimeException("List Not Added to Prospect: " + listName, true);
    }

    public void clickCreateProspectButton (Selenium selenium) {
        reporting.writeStep("Save Prospect");
        reporting.writeInfo("Click Create Prospect Button");
        selenium.click(createProspectCommitButton);
    }


}
