package com.example.email.contact;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public interface ContactOperation {

    boolean addContact(String userEmail, String contactEmail) throws IOException, ParseException;

    void deleteContact(String userEmail, String contactEmail) throws IOException, ParseException;

    boolean containContact(String userEmail, String contactEmail) throws IOException, ParseException;

}
