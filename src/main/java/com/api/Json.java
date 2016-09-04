package com.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Json {
    private String reportPath = ".\\reports\\";
    private String reportFilename = "Automation_Report.json";

    //private JSONObject obj = null;
    //private JSONArray jarr = null;
    private JSONParser parser = null;

    private JSONObject currentObject = null;
    private JSONArray rootArray = null;
    private JSONArray currentArray = null;

    private Reporting reporting;

    FileWriter file;

    public Json (Reporting reporting) {
        this.reporting = reporting;
    }

    public Json (Reporting reporting, String path, String filename) {
        this.reporting = reporting;

        currentObject = new JSONObject();
        currentArray = new JSONArray();

        if (filename != null) {
            reportFilename = filename;
        }
        if (path != null) {
            reportPath = path;
        }
    }

    public Json (String path, String filename) {
        currentObject = new JSONObject();
        currentArray = new JSONArray();

        if (filename != null) {
            reportFilename = filename;
        }
        if (path != null) {
            reportPath = path;
        }
    }

    public void addValue(String key, String value) {
        currentObject.put(key, value);
    }

    public void addValue(String key, Integer value) {
        currentObject.put(key, value);
    }

    public void addValue(String key, List<String> value) {
        JSONArray list = new JSONArray();
        for (String item : value) {
            list.add(item);
        }
        currentObject.put(key, value);
    }

    public void addArray() {
        currentArray.add(currentObject);
        currentObject = new JSONObject();
    }

    public void write() throws IOException {
        try {
            write(reportPath + reportFilename);
        } catch (IOException e) {
            throw e;
        }
    }

    public void write(String outputFile) throws IOException {
        try {
            file = new FileWriter(outputFile, true);
            currentArray.writeJSONString(file);
        } catch (IOException e) {
            throw e;
        }
    }

    public void flush() throws IOException {
        try {
            file.flush();
        } catch (IOException e) {
            throw e;
        }
    }

    public void close() throws IOException {
        try {
            file.close();
        } catch (IOException e) {
            throw e;
        }
    }






    public void open(String jsonString) {
        try {
            parser = new JSONParser();

            currentObject = (JSONObject)parser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getRootArray(String key) {
        rootArray = (JSONArray)currentObject.get(key);
        currentArray = (JSONArray)currentObject.get(key);
    }

    public void getArray(String key) {
        Iterator iterator = currentArray.iterator();
        while (iterator.hasNext()) {
            currentArray = (JSONArray)((JSONObject)iterator.next()).get(key);
        }
    }

    public Object getValue(String key) {
        return currentObject.get(key);
    }

    public List<Object> getArrayValues() {
        List<Object> list = new ArrayList<Object>();

        Iterator iterator = currentArray.iterator();
        while (iterator.hasNext()) {
            //System.out.println(iterator.next());
            list.add(iterator.next());
        }

        return list;
    }

    public List<Object> getArrayValues(String key) {
        List<Object> list = new ArrayList<Object>();

        Iterator iterator = currentArray.iterator();
        while (iterator.hasNext()) {
            JSONObject temp = (JSONObject) iterator.next();
            list.add(temp.get(key));
        }

        return list;
    }

}
