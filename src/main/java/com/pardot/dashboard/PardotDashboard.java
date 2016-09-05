package com.pardot.dashboard;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;

public class PardotDashboard {
    private Reporting reporting;

    private By userAccountMenuDropdown = By.id("menu-account");
    private String pageTitleText = "Dashboard";

    private By marketingMenu = By.id("mark-tog");
    private By marketingSegmentationMenu = By.linkText("Segmentation");
    private By marketingSegmentationListsMenu = By.linkText("Lists");
    private By marketingEmails = By.linkText("Emails");

    private By prospectsMenu = By.id("pro-tog");
    private By prospectsProspectList = By.linkText("Prospect List");

    private By signOut = By.linkText("Sign Out");

    private By waitIndicator = By.id("indicator");

    public PardotDashboard(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isDashboardPageLoaded(Selenium selenium) {
        reporting.writeInfo("---> Verify Dashboard Page Loaded");
        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("-----> Verify Dashboard Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void clickMarketingEmails(Selenium selenium) throws InterruptedException {
        reporting.writeStep("---> Navigate to Marketing\\Emails");

        reporting.writeInfo("-----> Hover Over Marketing Menu Item");
        selenium.mouseHover(marketingMenu);

        Thread.sleep(500);
        reporting.writeInfo("-----> Click Emails Menu Item");
        selenium.click(marketingEmails);
    }

    public void clickMarketingSegmentationLists(Selenium selenium) throws InterruptedException {
        reporting.writeStep("---> Navigate to Marketing\\Segmentation\\Lists");

        reporting.writeInfo("-----> Hover Over Marketing Menu Item");
        selenium.mouseHover(marketingMenu);

        reporting.writeInfo("-----> Hover Over Segmentation Menu Item");
        selenium.mouseHover(marketingSegmentationMenu);

        Thread.sleep(500);
        reporting.writeInfo("-----> Click Lists Menu Item");
        selenium.click(marketingSegmentationListsMenu);
    }

    public void clickProspectsProspectList(Selenium selenium) throws InterruptedException {
        reporting.writeStep("---> Navigate to Prospects\\Prospect List");

        reporting.writeInfo("-----> Hover Over Prospects Menu Item");
        selenium.mouseHover(prospectsMenu);

        Thread.sleep(500);
        reporting.writeInfo("-----> Click Prospects List Menu Item");
        selenium.click(prospectsProspectList);
    }

    public void signOut(Selenium selenium) throws InterruptedException {
        reporting.writeStep("---> Sign Out of Pardot");

        reporting.writeInfo("-----> Hover Over User Account Menu Item");
        selenium.mouseHover(userAccountMenuDropdown);

        Thread.sleep(500);
        reporting.writeInfo("-----> Click Sign Out Link");
        selenium.click(signOut);
    }

}
