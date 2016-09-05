package com.pardot.marketing.emails;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotEmails {
    private Reporting reporting;

    private String pageTitleText = "Emails";
    private By sendListEmailButton = By.xpath(".//*[@id='em_module']/div[1]/span/a");
    private By waitIndicator = By.id("indicator");

    public PardotEmails(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isEmailsPageLoaded(Selenium selenium) {
        reporting.writeStep("Verify Emails Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify Emails Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void clickSendListEmailButton(Selenium selenium) {
        reporting.writeStep("Open Basic Email Information Modal");

        reporting.writeInfo("Click Send List Email Button");
        selenium.click(sendListEmailButton);
    }



}
