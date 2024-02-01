package com.example.email.operations;

import com.example.email.users.Mail;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Operation {
    void makingImportant(String nameFolder, String userEmail, Long id) throws IOException, ParseException;

    void makingUnImportant(String nameFolder, String userEmail, Long id) throws IOException, ParseException;

    void makingRead(String nameFolder, String userEmail, Long id) throws IOException, ParseException;

    void makingUnRead(String nameFolder, String userEmail, Long id) throws IOException, ParseException;
    public void draftingMail(String userEmail, Mail mail) throws IOException, ParseException;
    public void deletingMail(String nameFolder, String userEmail, Long id) throws IOException, ParseException;
    public JSONArray search(String nameFolder, String userEmail, String searchKey) throws IOException, ParseException;

}
