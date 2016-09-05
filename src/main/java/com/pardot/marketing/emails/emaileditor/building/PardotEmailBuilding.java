package com.pardot.marketing.emails.emaileditor.building;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotEmailBuilding {
    private Reporting reporting;

    private By emailNameHeader = By.id("control_name");
    private String pageTitleText = "Building";

    private By headerSendingButton = By.id("flow_sending");

    private By waitIndicator = By.id("indicator");

    public PardotEmailBuilding(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isEmailBuildingPageLoaded(Selenium selenium, String emailName) {
        reporting.writeInfo("---> Verify Email Building Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("-----> Verify Email Building Page Title Contains: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }

        reporting.writeInfo("-----> Verify Email Name is: " + emailName);
        if (!selenium.getText(emailNameHeader).contains(emailName)) {
            selenium.throwRuntimeException("Email is Not: " + emailName, true);
        } else {
            reporting.writePass("Email Name Found");
        }
    }

    public void clickSendingButton(Selenium selenium) {
        reporting.writeStep("---> Click Header Sending Button");
        selenium.click(headerSendingButton);
    }

}
