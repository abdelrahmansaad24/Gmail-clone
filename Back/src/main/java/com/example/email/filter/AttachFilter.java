package com.example.email.filter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class AttachFilter implements Filter{
    @Override
    public JSONArray meetFilter(JSONArray mails, String FilterKey) throws IOException, ParseException {
        JSONArray filteredMails = new JSONArray();
        for (int i = 0; i < mails.size(); i++){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            String hasAttachment = jsonMail.get("hasAttachment").toString();
            if (hasAttachment.equals(FilterKey)){
                filteredMails.add(jsonMail);
            }
        }
        return filteredMails;
    }
}
