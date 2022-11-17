package com.memesKenya.meme.controller;

import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.model.MessageModel;
import com.memesKenya.meme.service._service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping("/messages")
    public List<Message> messagesByNickName(@RequestParam("nickname") String nickName){
        return service.getMessagesByNickName(nickName);
    }

    @GetMapping("/messages/guests")
    public List<Message> messagesBySender(@RequestParam("sender") String nickName,@RequestParam("receiver") String receiver){
        return service.getMessagesBySender(nickName,receiver);
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody() MessageModel message){
        return service.sendMessage(message);
    }

    @GetMapping("/message/you")
    public List<Message> getMessageContaining(@RequestParam("content") String content,@RequestParam("nickName") String nickName){
        return service.getMessageContaining(content,nickName);
    }
}
