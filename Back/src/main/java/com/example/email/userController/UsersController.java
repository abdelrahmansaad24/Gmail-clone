package com.example.email.userController;


import com.example.email.criteria.Criteria;
import com.example.email.users.Mail;
import com.example.email.users.User;
import com.example.email.Service.UsersService;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin()
public class UsersController {
    private final UsersService usersService;
    private Criteria criteria;

    public UsersController() {
        this.usersService = UsersService.getInstance();
    }

    @PostMapping("signup")
    public boolean signUp(@RequestBody User user) throws IOException, ParseException {
        return usersService.signUp(user);
    }
    
    @PostMapping("login")
    public boolean logIn(@RequestBody User user) throws IOException, ParseException {
        return usersService.logIn(user);
    }

    @PostMapping("sendingmail/{senderEmail}")
    public boolean sendingMail(@PathVariable String senderEmail, @RequestBody Mail mail) throws IOException, ParseException {
//        System.out.println(mail);
        return usersService.sendingMail(senderEmail, mail);
    }

    @GetMapping("inbox/{userEmail}")
    public JSONArray getInbox(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getInbox(userEmail);
    }
    @GetMapping("sent/{userEmail}")
    public JSONArray getSent(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getSent(userEmail);
    }
    @GetMapping("draft/{userEmail}")
    public JSONArray getDraft(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getDraft(userEmail);
    }
    @GetMapping("trash/{userEmail}")
    public JSONArray getTrash(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getTrash(userEmail);
    }
    @GetMapping("important/{nameTap}/{userEmail}")
    public JSONArray getImportant(@PathVariable String nameTap, @PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getImportant(nameTap, userEmail);
    }
    @GetMapping("catchImportant/{userEmail}")
    public JSONArray catchImportant(@PathVariable String userEmail) throws IOException, ParseException {
        System.out.println("ok1");
        System.out.println(usersService.catchImportant(userEmail));
        return usersService.catchImportant(userEmail);
    }
    @GetMapping("read/{nameTap}/{userEmail}")
    public JSONArray getRead(@PathVariable String nameTap, @PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getRead(nameTap, userEmail);
    }
    @GetMapping("unread/{nameTap}/{userEmail}")
    public JSONArray getunRead(@PathVariable String nameTap, @PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getunRead(nameTap, userEmail);
    }
    @PostMapping("draftingmail/{userEmail}")
    public void draftingMail(@PathVariable String userEmail, @RequestBody Mail mail) throws IOException, ParseException {
        usersService.draftingMail(userEmail, mail);
    }
    @DeleteMapping("deletingmail/{nameTap}/{userEmail}/{id}")
    public void deletingMail(@PathVariable String nameTap, @PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.deletingMail(nameTap, userEmail, id);
    }
    @DeleteMapping("deleteMailFolder/{userEmail}/{nameFolder}/{id}")
    public void deleteMailFolder(@PathVariable String userEmail, @PathVariable String nameFolder, @PathVariable Long id) throws IOException, ParseException {
        usersService.deleteMailFolder(nameFolder, userEmail, id);
    }
    @GetMapping("makingimportant/{nameTap}/{userEmail}/{id}")
    public void makingImportant(@PathVariable String nameTap, @PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingImportant(nameTap, userEmail, id);
    }
    @GetMapping("makingunimportant/{nameTap}/{userEmail}/{id}")
    public void makingUnImportant(@PathVariable String nameTap, @PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingUnImportant(nameTap, userEmail, id);
    }
    @GetMapping("makingread/{nameTap}/{userEmail}/{id}")
    public void makingRead(@PathVariable String nameTap, @PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingRead(nameTap, userEmail, id);
    }
    @GetMapping("search/{nameTap}/{userEmail}/{searchKey}")
    public JSONArray search(@PathVariable String nameTap, @PathVariable String userEmail, @PathVariable String searchKey) throws IOException, ParseException {
        return usersService.search(nameTap, userEmail, searchKey);
    }

    @PostMapping("allSearch/{nameTap}/{userEmail}")
    public JSONArray allSearch(@PathVariable String nameTap, @PathVariable String userEmail, @RequestBody Mail mail) throws IOException, ParseException {
        JSONArray  jsonArray = usersService.allSearch(nameTap, userEmail, mail);
        return jsonArray;
    }
    @GetMapping("makingunread/{nameTap}/{userEmail}/{id}")
    public void makingUnread(@PathVariable String nameTap, @PathVariable String userEmail, @PathVariable Long id) throws IOException, ParseException {
        usersService.makingUnread(nameTap, userEmail,id);
    }
    @GetMapping("sortDate/{nameTap}/{userEmail}")
    public JSONArray sortDate(@PathVariable String nameTap, @PathVariable String userEmail) throws IOException, ParseException {
        return usersService.sortDate(nameTap, userEmail);
    }
    @GetMapping("sortimportance/{nameTap}/{userEmail}")
    public JSONArray sortImportance(@PathVariable String nameTap, @PathVariable String userEmail) throws IOException, ParseException {
        return usersService.sortImportance(nameTap, userEmail);
    }
    @GetMapping("contacts/{userEmail}")
    public JSONArray getcontacts(@PathVariable String userEmail) throws IOException, ParseException {
        return usersService.getcontacts(userEmail);
    }
    @GetMapping("createFolder/{userEmail}/{nameFolder}")
    public boolean createFolder(@PathVariable String userEmail, @PathVariable String nameFolder) throws IOException, ParseException {
        System.out.println("create folder");
        return usersService.createFolder(userEmail, nameFolder);
    }
    @GetMapping("deleteFolder/{userEmail}/{nameFolder}")
    public boolean deleteFolder(@PathVariable String userEmail, @PathVariable String nameFolder) throws IOException, ParseException {
        System.out.println("delete Folder");
        return usersService.deleteFolder(userEmail, nameFolder);
    }

    @GetMapping("renameFolder/{userEmail}/{nameFolder}/{newName}")
    public boolean renameFolder(@PathVariable String userEmail, @PathVariable String nameFolder, @PathVariable String newName) throws IOException, ParseException {
        System.out.println("rename Folder");
        System.out.println(nameFolder);
        return usersService.renameFolder(userEmail, nameFolder, newName);
    }
    @GetMapping("getFoldersNames/{userEmail}")
    public JSONArray getFoldersNames(@PathVariable String userEmail) throws IOException, ParseException {
        System.out.println("return folders names");
        return usersService.getFoldersNames(userEmail);
    }
    @GetMapping("containFolder/{userEmail}/{nameFolder}")
    public boolean containFolder(@PathVariable String userEmail, @PathVariable String nameFolder) throws IOException, ParseException {
        System.out.println("contain folder");
        return usersService.containFolder(userEmail, nameFolder);
    }
    @GetMapping("getFolderMails/{userEmail}/{nameFolder}")
    public JSONArray getFolderMails(@PathVariable String userEmail, @PathVariable String nameFolder) throws IOException, ParseException {
        System.out.println("return folder mails");
        return usersService.getFolderMails(userEmail, nameFolder);
    }

    @GetMapping("addEmailToFolder/{userEmail}/{nameFolder}/{nameTap}/{id}")
    public boolean addEmailToFolder(@PathVariable String userEmail, @PathVariable String nameFolder, @PathVariable String nameTap, @PathVariable Long id) throws IOException, ParseException {
        System.out.println("move mail to folder");
        return usersService.addEmailToFolder(userEmail, nameFolder, nameTap, id);
    }
    @PostMapping("addcontact/{userEmail}/{contactEmail}")
    public boolean addContact(@PathVariable String userEmail, @PathVariable String contactEmail) throws IOException, ParseException {
        return usersService.addContact(userEmail, contactEmail);
    }
    @DeleteMapping("deletecontact/{userEmail}/{contactEmail}")
    public void deleteContact(@PathVariable String userEmail, @PathVariable String contactEmail) throws IOException, ParseException {
        usersService.deleteContact(userEmail, contactEmail);
    }
    //----------------------
    // attachment manipulation
    @PostMapping("/uploadattachment/{senderEmail}/{receiverEmail}/{id}")
    public void uploadAttachments(@RequestParam("files")List<MultipartFile> multipartFiles
            , @PathVariable String senderEmail
            , @PathVariable String receiverEmail
            , @PathVariable long id) throws IOException, ParseException {
        usersService.uploadAttachments(multipartFiles, senderEmail, receiverEmail, id);
    }
    @GetMapping("downloadattachment/{userEmail}/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable String userEmail, @PathVariable("id") long id) throws IOException {
        return usersService.downloadAttachment(userEmail, id);
    }



}
