package com.example.service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MessageService {
    MessageRepository MessageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.MessageRepository = messageRepository;
    }

    public Message persistMessage(Message m){
        return MessageRepository.save(m);
    }

    public void updateMessageText(Message m, String text){
        m.setMessageText(text);
        MessageRepository.save(m);
    }

    public Message getMessageById(Integer id){
        Optional<Message> m = MessageRepository.findById(id);
        if(m.isPresent()){
            return m.get();
        }
        else{
            return null;
        }
        
    }

    public void deleteMessageById(Integer id){
        MessageRepository.deleteById(id);
    }

    public List<Message> allMessages(){
        return MessageRepository.findAll();
    }

    public List<Message> userMessages(Integer id){
        return MessageRepository.userMessages(id);
    }


}
