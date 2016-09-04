package com.api;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.relevantcodes.extentreports.DisplayOrder.NEWEST_FIRST;
import static com.relevantcodes.extentreports.LogStatus.*;
import static com.relevantcodes.extentreports.NetworkMode.OFFLINE;

public class Reporting {
    private ExtentReports report;
    private ExtentTest testReport;
    public String reportPath = ".\\reports\\";
    public String reportFilenameHtml = "Automation_Report.html";
    public String screenshotPath = ".\\reports\\screenshots\\";

    private String randomUUIDString = UUID.randomUUID().toString();

    public Reporting() {
        report = new ExtentReports(reportPath + reportFilenameHtml, false, NEWEST_FIRST, OFFLINE );
    }

    public void writeInfo(String message) {
        writeLogEntry(INFO, message, false);
    }

    public void writeStep(String message) {
        writeLogEntry(INFO, message, true);
    }

    public void writeError(String message) {
        writeLogEntry(ERROR, message, false);
    }

    public void writeError(String message, String screenshot) {
        writeLogEntry(ERROR, message, screenshot, false);
    }

    public void writePass(String message) {
        writeLogEntry(PASS, message, false);
    }

    public void writePass(String message, String screenshot) {
        writeLogEntry(PASS, message, screenshot, false);
    }

    public void writeFail(String message) {
        writeLogEntry(FAIL, message, false);
    }

    public void writeFail(String message, String screenshot) {
        writeLogEntry(FAIL, message, screenshot, false);
    }

    public void writeWarning(String message) {
        writeLogEntry(WARNING, message, false);
    }

    public void writeWarning(String message, String screenshot) {
        writeLogEntry(WARNING, message, screenshot, false);
    }

    public void writeFatal(String message) {
        writeLogEntry(FATAL, message, false);
    }

    public void writeFatal(String message, String screenshot) {
        writeLogEntry(FATAL, message, screenshot, false);
    }

    public void writeSkip(String message) {
        writeLogEntry(SKIP, message, false);
    }

    public void writeSkip(String message, String screenshot) {
        writeLogEntry(SKIP, message, screenshot, false);
    }

    private void writeLogEntry(LogStatus logStatus, String message, Boolean isStep) {
        if (logStatus == FAIL) {
             message = "FAIL: " + message;
        }

        if (logStatus == FATAL) {
            message = "FATAL: " + message;
        }

        if (logStatus == PASS) {
            message = "PASS: " + message;
        }

        System.out.println(message);
        testReport.log(logStatus, message);
    }

    private void writeLogEntry(LogStatus logStatus, String message, String screenshot, Boolean isStep) {
        if (logStatus == FAIL) {
            message = "FAIL: " + message;
        }

        if (logStatus == FATAL) {
            message = "FATAL: " + message;
        }

        if (logStatus == PASS) {
            message = "PASS: " + message;
        }

        System.out.println(message);
        testReport.log(logStatus, message);
        testReport.log(logStatus, "Screenshot: " + testReport.addScreenCapture(screenshot));
    }

    public void startTest(String testName, String description) {
        testReport = report.startTest(testName, description);
        testReport.log(INFO, "Test Identifier: " + randomUUIDString);
    }

    public void endTest() throws IOException {
        report.endTest(testReport);
    }

    public void flush() throws IOException {
        report.flush();
    }

    public void close() throws IOException {
        report.close();
    }

    public void exceptionReportingFatal(String message) {
        writeFatal(message);
    }

    public void exceptionReportingFail(String message, String screenshot) {
        writeFail(message, screenshot.replace(reportPath, ".\\"));
    }

    public void exceptionReportingFail(String message) {
        writeFail(message);
    }
}
