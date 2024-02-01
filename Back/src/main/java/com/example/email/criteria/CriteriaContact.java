package com.example.email.criteria;

import com.example.email.criteria.Criteria;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class CriteriaContact implements Criteria {
    @Override
    public JSONArray meetCriteria(String userEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray contacts = (JSONArray) account.get("contacts");
        return contacts;
    }

    @Override
    public JSONArray meetCriteria(String nameFolder, String userEmail) throws IOException, ParseException {
        return null;
    }
}
