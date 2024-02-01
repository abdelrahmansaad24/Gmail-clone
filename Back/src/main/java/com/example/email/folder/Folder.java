package com.example.email.folder;

import com.example.email.users.Mail;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String name;
    private List<Mail> mails;

    public Folder(String name) {
        this.name = name;
        this.mails = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mail> getMails() {
        return mails;
    }

    public void setMails(List<Mail> mails) {
        this.mails = mails;
    }
    @Override
    public String toString() {
        return "{" +
                "\"name\": " + "\"" + name + "\"" +
                ", \"mails\": " + mails +
                '}';
    }
}
