package com.example.email.contact;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Contact implements ContactOperation {

    @Override
    public boolean addContact(String userEmail, String contactEmail) throws IOException, ParseException {
        String filename = "users.json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONArray users = (JSONArray) objc;
        filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc2 = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc2;
        JSONArray contacts = (JSONArray) account.get("contacts");
        boolean flage = false;
        for(int i = 0; i < users.size(); ++i){
            Object user = users.get(i);
            JSONObject jsonUser = (JSONObject) user;
            if(jsonUser.get("userEmail").equals(contactEmail)){
                jsonUser.remove("userPassword");
                contacts.add(jsonUser);
                flage = true;
                break;
            }
        }
        account.put("contacts", contacts);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flage;
    }

    @Override
    public void deleteContact(String userEmail, String contactEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray contacts = (JSONArray) account.get("contacts");
        for(int i = 0; i < contacts.size(); ++i){
            Object user = contacts.get(i);
            JSONObject jsonContact = (JSONObject) user;
            if(jsonContact.get("userEmail").equals(contactEmail)){
                contacts.remove(i);
                break;
            }
        }
        account.put("contacts", contacts);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containContact(String userEmail, String contactEmail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray contacts = (JSONArray) account.get("contacts");
        for(int i = 0; i < contacts.size(); ++i){
            Object user = contacts.get(i);
            JSONObject jsonContact = (JSONObject) user;
            if(jsonContact.get("userEmail").equals(contactEmail)){
                return true;
            }
        }
        return false;
    }
}
