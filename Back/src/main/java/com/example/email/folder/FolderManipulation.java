package com.example.email.folder;

import com.example.email.users.Mail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FolderManipulation {

    public synchronized boolean createFolder(String userEmail, String nameFolder) throws IOException, ParseException {
        Folder folder = new Folder(nameFolder);
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        folders.add(folder);
        account.put("folders", folders);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public  synchronized JSONArray getFoldersNames(String userEmail) throws IOException, ParseException {
        JSONArray names = new JSONArray();
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        for (int i = 0; i < folders.size(); i++){
            Object folder = folders.get(i);
            JSONObject jsonFolder = (JSONObject) folder;
            names.add(jsonFolder.get("name"));
        }
        System.out.println(names);
        return names;
    }
    public synchronized JSONArray getFolderMails(String userEmail, String nameFolder) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        System.out.println("kddkkk");
        for (int i = 0; i < folders.size(); i++){
            Object folder = folders.get(i);
            JSONObject jsonFolder = (JSONObject) folder;
            if (jsonFolder.get("name").equals(nameFolder)){
                System.out.println("returened, " + jsonFolder.get("name"));
                return (JSONArray) jsonFolder.get("mails");
            }
        }
        return null;
    }

    public synchronized boolean deleteFolder(String userEmail, String nameFolder) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        for (int i = 0; i < folders.size(); i++){
            Object folder = folders.get(i);
            JSONObject jsonFolder = (JSONObject) folder;
            if (jsonFolder.get("name").equals(nameFolder)){
                folders.remove(i);
                break;
            }
        }
        account.put("folders", folders);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public synchronized boolean containFolder(String userEmail, String nameFolder) throws IOException, ParseException {
        JSONArray names = new JSONArray();
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        for (int i = 0; i < folders.size(); i++){
            Object folder = folders.get(i);
            JSONObject jsonFolder = (JSONObject) folder;
            if (jsonFolder.get("name").equals(nameFolder)){
                return true;
            }
        }
        //System.out.println(names);
        return false;
    }
    public synchronized boolean renameFolder(String userEmail, String nameFolder, String newName) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        boolean flag = false;
        System.out.println(nameFolder);
        for (int i = 0; i < folders.size(); i++){
            Object folder = folders.get(i);
            JSONObject jsonFolder = (JSONObject) folder;
            System.out.println(jsonFolder.get("name"));

            if (jsonFolder.get("name").equals(nameFolder)){
                folders.remove(i);
                jsonFolder.put("name", newName);
                folders.add(jsonFolder);
                flag = true;
                break;
            }
        }
        if (flag){
            account.put("folders", folders);
            try {
                Files.write(Paths.get(filename), account.toJSONString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        //System.out.println(names);
        return false;
    }

    public synchronized boolean addEmailToFolder(String userEmail, String nameFolder, String nameTap, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray mails = (JSONArray) account.get(nameTap);

        JSONObject mail = null;
        for(int i = 0; i < mails.size(); ++i){
            Object userMail = mails.get(i);
            JSONObject jsonMail = (JSONObject) userMail;
            if (jsonMail.get("id").equals(id)){
                mail = jsonMail;
                break;
            }
        }

        boolean flag = false;
        if (mail != null){
            JSONArray folders = (JSONArray) account.get("folders");
            for (int i = 0; i < folders.size(); i++){
                Object folder = folders.get(i);
                JSONObject jsonFolder = (JSONObject) folder;
                if (jsonFolder.get("name").equals(nameFolder)){  // folder founded
                    JSONArray folderMails = (JSONArray) jsonFolder.get("mails");

                    flag = true;
                    folders.remove(i);
                    folderMails.add(mail);
                    jsonFolder.put("mails", folderMails);
                    folders.add(jsonFolder);
                    break;
                }
            }
            if (flag){
                System.out.println("ok");
                System.out.println(nameFolder);
                account.put("folders", folders);
                try {
                    Files.write(Paths.get(filename), account.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
    public boolean deleteEmail(String userEmail, String nameFolder, Long id) throws IOException, ParseException {
        String filename = "accounts/" + userEmail + "/" + userEmail + ".json";
        Object objc = new JSONParser().parse(new FileReader(filename));
        JSONObject account = (JSONObject) objc;
        JSONArray folders = (JSONArray) account.get("folders");
        boolean flag = false;
        for (int i = 0; i < folders.size(); i++){
            Object folder = folders.get(i);
            JSONObject jsonFolder = (JSONObject) folder;

            if (jsonFolder.get("name").equals(nameFolder)){
                folders.remove(i);
                JSONArray mails = (JSONArray) jsonFolder.get("mails");
                for (int j = 0; j < mails.size(); j++){
                    Object mail = mails.get(j);
                    JSONObject jsonMail = (JSONObject) mail;
                    if (jsonMail.get("id").equals(id)){
                        mails.remove(j);
                        break;
                    }
                }
                jsonFolder.put("mails", mails);
                folders.add(jsonFolder);
                flag = true;
                break;
            }
        }
        account.put("folders", folders);
        try {
            Files.write(Paths.get(filename), account.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
