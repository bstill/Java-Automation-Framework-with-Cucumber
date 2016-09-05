package com.pardot.prospects.prospect.overview;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotProspect {
    private Reporting reporting;

    private By listMenuButton = By.linkText("Lists");

    private By waitIndicator = By.id("indicator");

    public PardotProspect(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isProspectPageLoaded(Selenium selenium, String pageTitleText) {
        reporting.writeStep("Verify Prospect Page Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("Verify Prospect Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void clickListsMenu (Selenium selenium) {
        reporting.writeStep("Open Lists Page");
        reporting.writeInfo("Click Lists Menu Button");
        selenium.click(listMenuButton);
    }


}
