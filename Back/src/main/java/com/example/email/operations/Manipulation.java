package com.example.email.operations;

import com.example.email.users.Mail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Manipulation implements Operation {
    String datePattern = "dd/MM/yyyy HH:mm:ss ";
    DateTimeFormatter df = DateTimeFormatter.ofPattern(datePattern);
    @Override
    public synchronized void makingImportant(String nameFolder, String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        JSONObject jsonMail = null;
        boolean flag = false;
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                flag = true;
                jsonMail.put("important", true);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put(nameFolder, mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag){
            moveToImportant(userEmail, jsonMail);
        }
    }

    public synchronized void moveToImportant(String userEmail, JSONObject jsonMail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("important");
        if (mails.contains(jsonMail)){
            return;
        }
        mails.add(jsonMail);
        account.put("important", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void deleteFromImportant(String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("important");
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                mails.remove(i);
                break;
            }
        }
        account.put("important", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void makingUnImportant(String nameFolder, String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                jsonMail.put("important", false);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put(nameFolder, mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void makingRead(String nameFolder, String userEmail, Long id) throws IOException, ParseException {
        System.out.println("ok");
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                jsonMail.put("read", true);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put(nameFolder, mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void makingUnRead(String nameFolder, String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        for (int i = 0; i < mails.size(); ++i) {
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)) {
                jsonMail.put("read", false);
                mails.remove(i);
                mails.add(jsonMail);
                break;
            }
        }
        account.put(nameFolder, mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draftingMail(String userEmail, Mail mail) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get("draft");

        LocalDateTime now = LocalDateTime.now();
        String dateNow = df.format(now);
        mail.setDate(dateNow);
        mails.add(mail);
        account.put("draft", mails);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void deletingMail(String nameFolder, String userEmail, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        System.out.println(mails);
        JSONArray trashMails = (JSONArray) account.get("trash");
        boolean flag = false;
        JSONObject jsonMail = null;
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                flag = true;
                if (!nameFolder.equals("trash")){
                    trashMails.add(mails.get(i));
                }
                mails.remove(i);
                break;
            }
        }
        if (!nameFolder.equals("trash")){
            account.put("trash", trashMails);
        }
        account.put(nameFolder, mails);

        try {

            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag){
            deleteFromImportant(userEmail, id);
        }
        System.out.println(id);
        System.out.println(mails);
        System.out.println("-----------------");
    }

    @Override
    public synchronized JSONArray search(String nameFolder, String userEmail, String searchKey) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameFolder);
        JSONArray foundedMails = new JSONArray();
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            String body = (String) jsonMail.get("body");
            String sender = (String) jsonMail.get("sender");
            String receiver = (String) jsonMail.get("receiver");
            String subject = (String) jsonMail.get("subject");
            if (body.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }else if(subject.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }else if(sender.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }else if(receiver.contains(searchKey)){
                foundedMails.add(jsonMail);
                continue;
            }
        }
        return foundedMails;
    }
}
