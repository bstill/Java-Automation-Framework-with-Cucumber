package com.api;

import io.restassured.RestAssured;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestApi {
    private Reporting reporting;
    private Http http;
    private Json json;

    private String apiUrl;

    private RestAssured rest;

    private List nodes = new ArrayList();
    private Node responseHead;
    private Node currentNode;

    class Node {
        List<Node> nodes;
        Object data;
    }

    public RestApi (String url, Reporting reporting) {
        this.reporting = reporting;
        apiUrl = url;

        http = new Http(this.reporting);
        json = new Json(this.reporting);

        rest.baseURI = url;
    }

    public void connect(String method) throws IOException {
        http.openHttpConnection(apiUrl);
        http.setRequestMethod(method);
        http.setRequestProperty("Accept", "application/json");
    }

    public int getResponseCode() throws IOException {
        return http.getResponseCode();
    }

    public Boolean isResponse200() throws IOException {
        return isResponse(200);
    }

    public Boolean isResponse201() throws IOException {
        return isResponse(201);
    }

    public Boolean isResponse204() throws IOException {
        return isResponse(204);
    }

    public Boolean isResponse304() throws IOException {
        return isResponse(304);
    }

    public Boolean isResponse400() throws IOException {
        return isResponse(400);
    }

    public Boolean isResponse401() throws IOException {
        return isResponse(401);
    }

    public Boolean isResponse403() throws IOException {
        return isResponse(403);
    }

    public Boolean isResponse404() throws IOException {
        return isResponse(404);
    }

    public Boolean isResponse409() throws IOException {
        return isResponse(409);
    }

    public Boolean isResponse500() throws IOException {
        return isResponse(500);
    }

    public Boolean isResponse(int code) throws IOException {
        if (http.getResponseCode() == code) {
            return true;
        } else {
            return false;
        }
    }

    public void disconnect() {
        http.disconnect();
    }

    public void getServerResponse() throws IOException {
        http.openScanner();
        String resp =  http.readScanner();
        http.closeScanner();

       // response =  resp;

       // json.open(response);



    }







    public String getServerResponse_old() throws IOException {
        reporting.writeStep("Read Server Response");
        http.openScanner();
        String response =  http.readScanner();
        http.closeScanner();

        return response;
    }

    public void openJson(String jsonString) {
        reporting.writeStep("Open JSON Object");
        json.open(jsonString);
    }

    public void openJsonRootArray(String key){
        reporting.writeStep("Open JSON Root Array: " + key);
        json.getRootArray(key);
    }

    public void openJsonArray(String key){
        reporting.writeStep("Open JSON Array: " + key);
        json.getArray(key);
    }

    public void isJsonValue(String key, String expectedValue) {
        reporting.writeStep("Verify JSON Value is Expected: " + key + ": " + expectedValue);

        Boolean isFound = false;

        List<Object> array = json.getArrayValues(key);

        for (Object temp : array) {
            if (temp.equals(expectedValue)) {
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new RuntimeException(key + " value does not match expected value: " + expectedValue);
        }
    }

    public void isJsonValue(String expectedValue) {
        reporting.writeStep("Verify JSON Value is Expected: " + expectedValue);

        Boolean isFound = false;

        List<Object> array = json.getArrayValues();

        for (Object temp : array) {
            if (temp.equals(expectedValue)) {
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new RuntimeException("Value does not match expected value: " + expectedValue);
        }
    }

}
