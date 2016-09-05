package com.pardot.marketing.segmentation.lists.listinformation.selectfolder;

import com.api.Reporting;
import com.api.Selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PardotSelectFolder {
    private Reporting reporting;

    private By searchFieldClass = By.cssSelector("Input[class*='ember-text-field filter-by'");
    
    private By addFolderButton = By.xpath(".//*[@id='asset-chooser-app-modal']/div[3]/div/div[2]/div/div[2]/div/div[2]/div");
    private By selectFolderModalTitle = By.xpath(".//*[@id='asset-chooser-app-modal']/div[1]/h3");
    private String modalTitleText = "Select A Folder";
    private By chooseSelectedButton = By.id("select-asset");

    private By folderListContainer = By.className("ember-list-container");

    private By addNewFolderNameField = By.cssSelector("Input[class*='ember-text-field js-new-folder'");
    private By addNewFolderSaveButton = By.cssSelector("Div[class*='btn-success'");

    private By waitIndicator = By.id("indicator");

    public PardotSelectFolder(Reporting reporting) {
        this.reporting = reporting;
    }

    public void isSelectFolderModalLoaded(Selenium selenium) {
        reporting.writeInfo("---> Verify Select A Folder Loaded");

        selenium.waitElementInvisible(waitIndicator);

        reporting.writeInfo("-----> Verify Select Folder Modal Title is: " + modalTitleText);
        if (!selenium.getText(selectFolderModalTitle).equals(modalTitleText)) {
            selenium.throwRuntimeException("Modal Title is Not: " + modalTitleText + "(" + selenium.getText(selectFolderModalTitle) + ")", true);
        } else {
            reporting.writePass("Modal Title Found");
        }
    }

    public void isFolderExist(Selenium selenium, String folderName) {
        reporting.writeInfo("---> Verify Folder Exists");

        reporting.writeInfo("-----> Search for Folder: " + folderName);
        selenium.clear(searchFieldClass);
        selenium.sendKeys(searchFieldClass, folderName);

        selenium.waitElementInvisible(waitIndicator);

        List<WebElement> elements = selenium.findElements(folderListContainer, By.tagName("span"));

        for(WebElement e : elements){
            if (e.getText().equals(folderName)) {
                reporting.writePass("Folder Found");
                return;
            }
        }

        selenium.throwRuntimeException("Folder Not Found: " + folderName, true);
    }

    public void clickFolder(Selenium selenium, String folderName) {
        reporting.writeStep("---> Select Folder");

        reporting.writeInfo("-----> Search for Folder: " + folderName);
        selenium.clear(searchFieldClass);
        selenium.sendKeys(searchFieldClass, folderName);

        selenium.waitElementInvisible(waitIndicator);

        List<WebElement> elements = selenium.findElements(folderListContainer, By.tagName("span"));

        for(WebElement e : elements){
            if (e.getText().equals(folderName)) {
                reporting.writeInfo("-----> Click Folder");
                e.click();
                return;
            }
        }

        selenium.throwRuntimeException("Folder Not Found: " + folderName, true);
    }

    public void clickCreateFolderButton(Selenium selenium) {
        reporting.writeStep("-----> Click Add Folder Button");
        selenium.click(addFolderButton);
    }

    public void addNewFolder (Selenium selenium, String folderName) {
        reporting.writeStep("---> Add Folder");

        reporting.writeInfo("-----> Enter Folder Name");
        selenium.clear(addNewFolderNameField);
        selenium.sendKeys(addNewFolderNameField, folderName);

        reporting.writeInfo("-----> Save Folder");
        selenium.click(addNewFolderSaveButton);
    }

    public void clickChooseSelectedButton(Selenium selenium) {
        reporting.writeStep("-----> Click Choose Selected Button");
        selenium.click(chooseSelectedButton);
    }




}
