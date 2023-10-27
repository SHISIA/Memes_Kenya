package com.memesKenya.meme.controller;

import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.messaging.SenderAndReceiverModel;
import com.memesKenya.meme.messaging.SocketMessage;
import com.memesKenya.meme.messaging.Status;
import com.memesKenya.meme.model.DeleteModel;
import com.memesKenya.meme.model.MessageModel;
import com.memesKenya.meme.service._service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RestController
public class MessageChatController {

    private final MessageService messageService;

    public MessageChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public SocketMessage sendMessage(@Payload SocketMessage chatMessage) {
        chatMessage.setTimestamp(new Date()); // Set the current timestamp
        chatMessage.setStatus(Status.MESSAGE);
        System.out.println("messgae "+chatMessage.getSenderName()+" conent:"+
                chatMessage.getMessage());
        return chatMessage;
    }

    @PostMapping("/api/v1/addMessage")
    public String addMessage(@RequestBody MessageModel model){
        messageService.sendMessage(model);
        return model.toString()+" sent successfully";
    }

    @DeleteMapping("/api/v1/deleteMessage")
    public Map<String,String> deleteMessage(@RequestBody DeleteModel deleteModel){
        Map<String,String> message = new HashMap<>();
        messageService.deleteMessage(deleteModel.getSender(),deleteModel.getReceiver());
        message.put("SocketMessage","Deleted Successfully");
        return message;
    }

    @GetMapping("/api/v1/getMessagesBySenderAndReceiver")
    public Optional<List<Message>> getMessagesBySenderAndReceiver(@RequestBody SenderAndReceiverModel senderAndReceiverModel){
        return messageService.getMessageBySenderAndReceiver(
                senderAndReceiverModel.getSender(),
                senderAndReceiverModel.getReceiver());
    }

    @GetMapping("/api/v1/getMessageBySender")
    public Optional<List<Message>> getMessagesBySender(@RequestParam UUID senderId){
        Optional<List<Message>> list = messageService.getMessagesBySender(senderId);
        System.out.println("size "+list.get().size());
        return list;
    }

}
