package com.example.email.criteria;

import com.example.email.criteria.Criteria;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CriteriaTrash implements Criteria {
    String datePattern = "dd/MM/yyyy HH:mm:ss ";
    DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);
    @Override
    public JSONArray meetCriteria(String userEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("trash");
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            String date = jsonMail.get("date").toString();
            LocalDateTime dateParsed = LocalDateTime.parse(date, df);
            if(dateParsed.getDayOfMonth() - LocalDateTime.now().getDayOfMonth() < -29){
                mails.remove(i);
            }
        }
        account.put("trash", mails);
        return mails;
    }

    @Override
    public JSONArray meetCriteria(String nameFolder, String userEmail) throws IOException, ParseException {
        return null;
    }
}
