package com.example.email.filter;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Filter {
    JSONArray meetFilter(JSONArray mails, String FilterKey) throws IOException, ParseException;
}
