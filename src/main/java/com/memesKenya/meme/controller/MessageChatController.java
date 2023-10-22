package com.memesKenya.meme.controller;

import com.memesKenya.meme.messaging.Message;
import com.memesKenya.meme.messaging.Status;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class MessageChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload Message chatMessage) {
        chatMessage.setTimestamp(new Date()); // Set the current timestamp
        chatMessage.setStatus(Status.MESSAGE);
        System.out.println("messgae "+chatMessage.getSenderName()+" conent:"+
                chatMessage.getMessage());
        return chatMessage;
    }

}
