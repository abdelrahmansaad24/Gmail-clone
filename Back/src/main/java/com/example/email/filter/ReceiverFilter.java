package com.example.email.filter;

import com.example.email.users.Mail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ReceiverFilter implements Filter{
    @Override
    public JSONArray meetFilter(JSONArray mails, String FilterKey) throws IOException, ParseException {
        if (FilterKey == null)
            return mails;
        JSONArray filteredMails = new JSONArray();
        for (int i = 0; i < mails.size(); i++){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            String receiver = jsonMail.get("receiver").toString();
            if (receiver.contains(FilterKey)){
                filteredMails.add(jsonMail);
            }
        }
        return filteredMails;
    }
}
