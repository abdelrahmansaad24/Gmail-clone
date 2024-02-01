package com.example.email.sort;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Sort implements Sorting {
    String datePattern = "dd/MM/yyyy HH:mm:ss ";
    DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);

    @Override
    public JSONArray sortImportance(String nameFolder, String userEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        mails.sort(new Comparator() {
            @Override
            public int compare(Object mail1, Object mail2) {
                JSONObject jsonMail1 = (JSONObject) mail1;
                JSONObject jsonMail2 = (JSONObject) mail2;
                long importance1 = (long) jsonMail1.get("importance");
                long importance2 = (long) jsonMail2.get("importance");
                if(importance2 > importance1) return 1;
                else return -1;
            }
        });
        return mails;
    }

    @Override
    public JSONArray sortDate(String nameFolder, String userEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        mails.sort(new Comparator() {
            @Override
            public int compare(Object mail1, Object mail2) {
                JSONObject jsonMail1 = (JSONObject) mail1;
                JSONObject jsonMail2 = (JSONObject) mail2;
                String date1 = (String) jsonMail1.get("date");
                String date2 = (String) jsonMail2.get("date");
                LocalDateTime date1Parsed = LocalDateTime.parse(date1, df);
                LocalDateTime date2Parsed = LocalDateTime.parse(date2, df);
                return date2Parsed.compareTo(date1Parsed);
            }
        });
        return mails;
    }
}
