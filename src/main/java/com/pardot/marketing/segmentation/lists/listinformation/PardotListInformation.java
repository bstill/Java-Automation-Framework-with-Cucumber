package com.pardot.marketing.segmentation.lists.listinformation;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotListInformation {
    private Reporting reporting;
    private By nameField = By.name("name");
    private By folderField = By.xpath(".//*[@id='li_form_update']/div[2]/div/div[1]/span[2]");
    private By createListButton = By.id("save_information");
    private By cancelButton = By.linkText("Cancel");
    private By listInformationModalTitle = By.id("myModalLabel");
    private String modalTitleText = "List Information";
    private By chooseFolderButton = By.xpath(".//*[@id='li_form_update']/div[2]/div/div[1]/button");

    private By createListErrorHeader = By.cssSelector("Div[class*='alert-error'"); //partial class name
    
    private By createListErrorName = By.id("error_for_name");
    private String createListDuplicateErrorText = "Please input a unique value for this field";
    private String createListErrorHeaderText = "Please correct the errors below and re-submit";

    private By waitIndicator = By.id("indicator");

    public PardotListInformation(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isListInformationModalLoaded(Selenium selenium) {
        reporting.writeStep("Verify List Information Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify List Information Modal Title is: " + modalTitleText);
        if (!selenium.getText(listInformationModalTitle).equals(modalTitleText)) {
            selenium.throwRuntimeException("Modal Title is Not: " + modalTitleText, true);
        } else {
            reporting.writePass("Modal Title Found");
        }
    }

    public void isListInformationModalPopulated(Selenium selenium, String listName, String folderName) {
        reporting.writeStep("Verify List Information Populated");

        reporting.writeInfo("Verify Name Field Value: " + listName);
        if (!selenium.getAttribute(nameField, "value").equals(listName)) {
            selenium.throwRuntimeException("Name Field Value is Not: " + listName, true);
        } else {
            reporting.writePass("Name Found");
        }

        reporting.writeInfo("Verify Folder Field Value: /" + folderName);
        if (!selenium.getText(folderField).equals("/" + folderName)) {
            selenium.throwRuntimeException("Folder Field Value is Not: /" + folderName, true);
        } else {
            reporting.writePass("Folder Found");
        }
    }

    public void isListInformationModalNotPopulated(Selenium selenium) {
        reporting.writeStep("Verify List Information NOT Populated");

        reporting.writeInfo("Verify Name Field Value is Blank");
        if (!selenium.getAttribute(nameField, "value").equals("")) {
            selenium.throwRuntimeException("Name Field Value is Not Blank", true);
        } else {
            reporting.writePass("Name Field Blank");
        }

        reporting.writeInfo("Verify Folder Field Value is Default");
        if (!selenium.getText(folderField).equals("/Uncategorized/Lists")) {
            selenium.throwRuntimeException("Folder Field Value is Not Default", true);
        } else {
            reporting.writePass("Folder Field Default");
        }
    }

    public void clickChooseFolderButton(Selenium selenium) {
        reporting.writeStep("Choose Folder");
        reporting.writeInfo("Click Choose Folder Button");
        selenium.click(chooseFolderButton);
    }

    public void isFolderSelected(Selenium selenium, String folderName) {
        reporting.writeStep("Verify Folder Field Contains Selected Folder");

        reporting.writeInfo("Verify Folder Field Value: /" + folderName);
        if (!selenium.getText(folderField).equals("/" + folderName)) {
            selenium.throwRuntimeException("Folder Field Value is Not: /" + folderName, true);
        } else {
            reporting.writePass("Folder Found");
        }
    }

    public void createList(Selenium selenium, String listName) {
        reporting.writeStep("Create/Edit Segmentation List");

        reporting.writeInfo("Enter List Name: " + listName);
        selenium.clear(nameField);
        selenium.sendKeys(nameField, listName);
    }

    public void saveList(Selenium selenium) {
        reporting.writeStep("Save Segmentation List");
        reporting.writeInfo("Click Create List Button");
        selenium.click(createListButton);
    }

    public void cancelList(Selenium selenium) {
        reporting.writeStep("Cancel Segmentation List");
        reporting.writeInfo("Click Cancel Button");
        selenium.click(cancelButton);
    }

    public void isListInformationDuplicateNameErrorDisplayed(Selenium selenium) {
        reporting.writeStep("Verify List Information Duplicate Name Error Displayed");

        reporting.writeInfo("Verify Header Error Message is: " + createListErrorHeaderText);
        if (!selenium.getText(createListErrorHeader).equals(createListErrorHeaderText)) {
            selenium.throwRuntimeException("Header Error Message is Not: " + createListErrorHeaderText, true);
        } else {
            reporting.writePass("Header Error Message Found");
        }

        reporting.writeInfo("Verify Name Error Message is: " + createListDuplicateErrorText);
        if (!selenium.getText(createListErrorName).equals(createListDuplicateErrorText)) {
            selenium.throwRuntimeException("Name Error Message is Not: " + createListDuplicateErrorText, true);
        } else {
            reporting.writePass("Name Error Message Found");
        }
    }


}
