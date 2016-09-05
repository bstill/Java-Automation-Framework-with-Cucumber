package com.pardot.prospects.list;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PardotProspects {
    private Reporting reporting;

    private String pageTitleText = "Prospects";

    private By addProspectButtonId = By.id("pr_link_create");

    private By prospectsFilterDateRangeDropdownId = By.id("dateRange_pr");

    private By prospectsFilterFieldName = By.name("table_filter");
    private By prospectsTableId = By.id("prospect_table");

    private By waitIndicatorId = By.id("indicator");

    public PardotProspects(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isProspectsPageLoaded(Selenium selenium) {
        reporting.writeInfo("---> Verify Prospects Page Loaded");

        selenium.waitElementInvisible(waitIndicatorId);

        reporting.writeInfo("-----> Verify Prospects Page Title is: " + pageTitleText);
        if (!selenium.getTitle().contains(pageTitleText)) {
            selenium.throwRuntimeException("Page Title is Not: " + pageTitleText, true);
        } else {
            reporting.writePass("Page Title Found");
        }
    }

    public void clickAddProspectButton(Selenium selenium) {
        reporting.writeStep("---> Open Create Prospect Page");

        reporting.writeInfo("-----> Click Add Prospect Button");
        selenium.click(addProspectButtonId);
    }

    public void isProspectExist(Selenium selenium, String prospectName) {
        reporting.writeInfo("---> Verify Prospect Exists");

        reporting.writeInfo("-----> Search for Prospect: " + prospectName);
        selenium.selectByVisibleText(prospectsFilterDateRangeDropdownId, "Today");
        selenium.clear(prospectsFilterFieldName);
        selenium.sendKeys(prospectsFilterFieldName, prospectName);

        selenium.waitElementInvisible(waitIndicatorId);

        List<WebElement> elements = selenium.findElements(prospectsTableId, By.tagName("tr"));

        for(WebElement e : elements){
            if (selenium.getText(e).contains(prospectName)) {
                reporting.writePass("Prospect Found");
                return;
            }
        }

        selenium.throwRuntimeException("Prospect Not Found: " + prospectName, true);
    }


}
