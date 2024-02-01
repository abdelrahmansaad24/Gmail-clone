package com.example.email.criteria;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Criteria {
    JSONArray meetCriteria(String userEmail) throws IOException, ParseException;
    JSONArray meetCriteria(String nameFolder, String userEmail) throws IOException, ParseException;
}