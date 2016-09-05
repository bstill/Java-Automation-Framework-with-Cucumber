package com.pardot.marketing.emails.emailinformation;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotBasicEmailInformation {
    private Reporting reporting;

    private By listInformationModalTitle = By.id("myModalLabel");
    private String modalTitleText = "Basic Email Information";

    private By nameField = By.name("name");
    private By emailTypeText = By.id("email_type_text_only");
    private By emailTypeHtml = By.id("email_type_text_html");
    private By useEmailTemplateCheckbox = By.name("from_template");
    private By saveEmailButton = By.id("save_information");
    private By folderField = By.xpath(".//*[@id='information_form']/div[3]/div/div/span[2]");
    private By chooseFolderButton = By.xpath(".//*[@id='information_form']/div[3]/div/div/button");
    private By campaignField = By.xpath(".//*[@id='information_form']/div[4]/div/div/span[2]");
    private By chooseCampaignButton = By.xpath(".//*[@id='information_form']/div[4]/div/div/button");

    private By waitIndicator = By.id("indicator");

    public PardotBasicEmailInformation(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isBasicEmailInformationModalLoaded(Selenium selenium) {
        reporting.writeInfo("---> Verify Email Information Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("-----> Verify Email Information Modal Title is: " + modalTitleText);
        if (!selenium.getText(listInformationModalTitle).contains(modalTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + modalTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void createEmail(Selenium selenium, String emailName, String emailType, Boolean useEmailTemplate) {
        reporting.writeStep("---> Create Email");

        reporting.writeInfo("-----> Enter Email Name: " + emailName);
        selenium.clear(nameField);
        selenium.sendKeys(nameField, emailName);

        reporting.writeInfo("-----> Select Email Type: " + emailType);
        if (emailType.equals("Text")) {
            selenium.click(emailTypeText);
        } else {
            selenium.click(emailTypeHtml);
        }

        reporting.writeInfo("-----> Select Use Email Template: " + useEmailTemplate.toString());
        if (useEmailTemplate) {
            if (!selenium.isSelected(useEmailTemplateCheckbox)) {
                selenium.click(useEmailTemplateCheckbox);
            }
        } else {
            if (selenium.isSelected(useEmailTemplateCheckbox)) {
                selenium.click(useEmailTemplateCheckbox);
            }
        }
    }

    public void clickSaveButton(Selenium selenium) {
        reporting.writeStep("---> Save Basic Email Information");
        selenium.click(saveEmailButton);
    }

    public void clickChooseFolderButton(Selenium selenium) {
        reporting.writeStep("---> Save Basic Email Information");
        selenium.click(chooseFolderButton);
    }

    public void isFolderSelected(Selenium selenium, String folderName) {
        reporting.writeInfo("---> Verify Folder Field Contains Selected Folder: /" + folderName);
        if (!selenium.getText(folderField).equals("/" + folderName)) {
            selenium.throwRuntimeException("Folder Name is Not: /" + folderName, true);
        } else {
            reporting.writePass("Folder Found");
        }
    }

    public void clickChooseCampaignButton(Selenium selenium) {
        reporting.writeStep("---> Save Basic Email Information");
        selenium.click(chooseCampaignButton);
    }

    public void isCampaignSelected(Selenium selenium, String campaignName) {
        reporting.writeInfo("---> Verify Campaign Field Contains Selected Campaign: " + campaignName);
        if (!selenium.getText(campaignField).equals(campaignName)) {
            selenium.throwRuntimeException("Campaign Name is Not: " + campaignName, true);
        } else {
            reporting.writePass("Campaign Found");
        }
    }


}
