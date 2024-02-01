package com.example.email.sort;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Sorting {
    public JSONArray sortImportance(String nameFolder, String userEmail) throws IOException, ParseException;
    public JSONArray sortDate(String nameFolder, String userEmail) throws IOException, ParseException;
}
