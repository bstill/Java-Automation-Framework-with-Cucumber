package com.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class Http {
    private Reporting reporting;
    private HttpURLConnection conn;
    private Scanner scanner;
    public URL url;

    public Http(Reporting reporting) {
        this.reporting = reporting;
    }

    public void openHttpConnection(String url_string) throws IOException {
        try {
            url = new URL(url_string);
            conn = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            reporting.exceptionReportingFatal("Malformed URL: " + url_string);
            throw e;
        } catch (IOException e) {
            reporting.exceptionReportingFatal("IO Exception Opening HTTP Connection");
            throw e;
        }
    }

    public void setRequestMethod(String method) {
        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            reporting.exceptionReportingFatal("Protocol Exception: " + method);
        }
    }

    public void setRequestProperty(String property, String value) {
        conn.setRequestProperty(property, value);
    }

    public Integer getResponseCode() throws IOException {
        try {
            return conn.getResponseCode();
        } catch (IOException e) {
            reporting.exceptionReportingFatal("IO Exception Getting Response Code");
            throw e;
        }
    }

    public void openScanner() throws IOException {
        try {
            scanner = new Scanner(url.openStream());
        } catch (IOException e) {
            reporting.exceptionReportingFatal("IO Exception Opening Scanner");
            throw e;
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    public String readScanner() {
        String response = new String();
        while (scanner.hasNext())
            response += scanner.nextLine();

        return response;
    }

    public void disconnect() {
        conn.disconnect();
    }

    public void throwRuntimeException(String message) {
        try {
            reporting.exceptionReportingFail(message);
            throw new RuntimeException (message);
        } catch (RuntimeException e) {
            throw e;
        }
    }

}
