package com.example.email.filter;

import com.example.email.users.Mail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class FilterDemo {

    Filter receiverFilter = new ReceiverFilter();
    Filter senderFilter = new SenderFilter();
    Filter subjectFilter = new SubjectFilter();
    Filter bodyFilter = new BodyFilter();
    Filter attachFilter = new AttachFilter();
    Filter dateFilter = new DateFilter();
    Filter importanceFilter = new ImportanceFilter();
    public JSONArray FilterPatternDemo(String nameFolder, String userEmail, Mail mail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder.toLowerCase());
        System.out.println(nameFolder);
        System.out.println(mails);
        System.out.println(mail.getReceiver());
        mails = receiverFilter.meetFilter(mails, mail.getReceiver());
        System.out.println(mail.getSender());
        mails = senderFilter.meetFilter(mails, mail.getSender());
        System.out.println(mail.getSubject());
        mails = subjectFilter.meetFilter(mails, mail.getSubject());
        System.out.println(mail.getBody());
        mails = bodyFilter.meetFilter(mails, mail.getBody());
        System.out.println(Boolean.toString(mail.isHasAttachment()));
        mails = attachFilter.meetFilter(mails, Boolean.toString(mail.isHasAttachment()));
        System.out.println(mail.getDate());
        mails = dateFilter.meetFilter(mails, mail.getDate());
        System.out.println(String.valueOf(mail.getImportance()));
        mails = importanceFilter.meetFilter(mails, String.valueOf(mail.getImportance()));
        System.out.println(mails);
        return mails;
    }
}
