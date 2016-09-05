package com.pardot.marketing.emails.emaileditor.sending;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PardotEmailSending {
    private Reporting reporting;

    private By emailNameHeader = By.id("control_name");
    private String pageTitleText = "Sending";

    private By sendNowButton = By.name("Send Now");

    private By listsDropdown = By.id("email-wizard-list-select");
    private By listsSearchDiv = By.className("chzn-search");
    private By listsSearchResultsDropdown = By.className("chzn-results");

    private By listsSelectedContainer = By.className("selected-lists");

    private By senderSelect = By.name("a_sender[]");

    private By subjectField = By.name("subject_a");

    private By waitIndicator = By.id("indicator");

    public PardotEmailSending(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isEmailSendingPageLoaded(Selenium selenium, String emailName) {
        reporting.writeStep("Verify Email Sending Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify Email Sending Page Title Contains: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }

        reporting.writeInfo("Verify Email Name is: " + emailName);
        if (!selenium.getText(emailNameHeader).contains(emailName)) {
            selenium.throwRuntimeException("Email Name is Not: " + emailName, true);
        } else {
            reporting.writePass("Email Name Found");
        }
    }

    public void clickSendNowButton(Selenium selenium) {
        reporting.writeStep("Send Email");
        reporting.writeInfo("Click Send Now Button");

        reporting.writeInfo("*Email Functionality Disabled");
        selenium.click(sendNowButton);
    }

    public void enterEmailToFrom(Selenium selenium, String listName, String sender, String subject) {
        reporting.writeStep("Enter Email To/From/Subject Data");

        reporting.writeInfo("Enter To: " + listName);
        reporting.writeInfo("Open List Dropdown");
        selenium.click(selenium.findChildElement(selenium.findElement(listsDropdown), By.tagName("b")));

        reporting.writeInfo("Search for List: " + listName);
        selenium.sendKeys(selenium.findChildElement(selenium.findElement(listsSearchDiv), By.tagName("input")), listName);

        reporting.writeInfo("List");
        selenium.doubleClick(listsSearchResultsDropdown);
        isListExist(selenium, listName);

        reporting.writeInfo("Enter From: " + sender);
        selenium.selectByVisibleText(senderSelect, sender);

        reporting.writeInfo("Enter Subject: " + subject);
        selenium.clear(subjectField);
        selenium.sendKeys(subjectField, subject);
    }

    public void isListExist(Selenium selenium, String listName) {
        reporting.writeStep("Check List Added to Email To Field");
        reporting.writeInfo("Verify List Added: " + listName);
        List<WebElement> elements = selenium.findElements(listsSelectedContainer, By.tagName("li"));

        for(WebElement e : elements){
            if (selenium.getAttribute(e, "data-name").equals(listName)) {
                reporting.writePass("Found List");
                return;
            }
        }

        selenium.throwRuntimeException("List Not Added to Email To Field: " + listName, true);
    }


}
