package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.websocket.server.PathParam;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    //Have Spring create the services the controller will use
    @Autowired
    AccountService aS;
    @Autowired
    MessageService mS;


    @PostMapping("/register")
    public Account register(@RequestBody Account a){
        
        
        if(aS.findAccount(a) != null && a.getPassword().length() >= 4 && a.getUsername() != ""){
            throw new registerUserDuplicateUsername();
        }
        else{
            return aS.persistAccount(a);
        }
        
    }

    @PostMapping("/login")
    public Account login(@RequestBody Account a){
        Account temp = aS.validLogin(a);
        if(temp != null){
            return temp;
        }
        else{
            throw new badLogin();
        }
    }

    @GetMapping("/messages")
    public List<Message> allMessages(){
        return mS.allMessages();
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message m){
        if(m.getMessageText() != "" && m.getMessageText().length() < 256 && aS.accountExists(m.getPostedBy())){
            return mS.persistMessage(m);            
        }
        else{
            throw new badMessage();
        }
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable("messageId") Integer id){
        Message temp = mS.getMessageById(id);
        if(temp != null){
            return temp;
        }
        else{
            return null;
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public Integer deleteMessageById(@PathVariable("messageId") Integer id){
        Message temp = mS.getMessageById(id);
        if(temp != null){
            mS.deleteMessageById(id);
            return 1;
        }
        else{
            return null;
        }
    }

    @PatchMapping("/messages/{messageId}")
    public Integer updateMessageById(@PathVariable("messageId") Integer id, @RequestBody Message m){
        Message temp = mS.getMessageById(id);
        if(temp != null){
            if(m.getMessageText() != "" && m.getMessageText().length() < 256 && aS.accountExists(temp.getPostedBy())){
                mS.updateMessageText(temp, m.getMessageText());
                return 1;            
            }
            else{
                throw new badMessage();
            }
        }
        else{
            throw new badMessage();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> messagesByUser(@PathVariable("accountId") Integer id){
        return mS.userMessages(id);
    }

    //Classes to set response status
    //409
    @ResponseStatus(HttpStatus.CONFLICT)
    public class registerUserDuplicateUsername extends RuntimeException {
        public registerUserDuplicateUsername(){}   
        
    }

    //401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class badLogin extends RuntimeException {
        public badLogin(){}   
        
    }

    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class badMessage extends RuntimeException {
        public badMessage(){}   
        
    }

}
