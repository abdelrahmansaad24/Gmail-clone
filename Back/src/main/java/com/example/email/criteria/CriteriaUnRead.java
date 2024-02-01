package com.example.email.criteria;

import com.example.email.criteria.Criteria;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class CriteriaUnRead implements Criteria {
    @Override
    public JSONArray meetCriteria(String userEmail) throws IOException, ParseException {
        return null;
    }

    @Override
    public JSONArray meetCriteria(String nameFolder, String userEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        JSONArray unreadMails = new JSONArray();
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (!(boolean) jsonMail.get("read")){
                unreadMails.add(jsonMail);
            }
        }
        return unreadMails;
    }
}
