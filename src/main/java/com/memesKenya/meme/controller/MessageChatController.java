package com.memesKenya.meme.controller;

import com.memesKenya.meme.messaging.SocketMessage;
import com.memesKenya.meme.messaging.Status;
import com.memesKenya.meme.model.DeleteModel;
import com.memesKenya.meme.service._service.MessageService;
import com.memesKenya.meme.service._service.SocketMessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RestController
public class MessageChatController {

    private final SocketMessageService messageService;

    public MessageChatController(SocketMessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat/private/{id}")
    @SendTo("/queue/{id}/reply")
    public SocketMessage sendMessage(@Payload SocketMessage chatMessage,
                                     @DestinationVariable("id") String receiverId) {
        chatMessage.setMessageId(UUID.randomUUID());
        chatMessage.setTimestamp(new Date()); // Set the current timestamp
        chatMessage.setStatus(Status.MESSAGE);
        System.out.println("get id" +receiverId);
        // Check if the sender and receiver match the chatting users
        return messageService.sendMessage(chatMessage);
    }

    @PostMapping("/api/v1/addMessage")
    public String addMessage(@RequestBody SocketMessage model){
        messageService.sendMessage(model);
        return model.toString()+" sent successfully";
    }

    @DeleteMapping("/api/v1/deleteMessage")
    public Map<String,String> deleteMessage(@RequestBody DeleteModel deleteModel){
        Map<String,String> message = new HashMap<>();
        messageService.deleteSocketMessage(deleteModel.getSender(),deleteModel.getReceiver());
        message.put("SocketMessage","Deleted Successfully");
        return message;
    }

    @GetMapping("/api/v1/getMessagesBySenderAndReceiver")
    public Optional<List<SocketMessage>> getMessagesBySenderAndReceiver(
            @RequestParam("sender") UUID sender,
            @RequestParam("receiver") UUID receiver) {
        // Use sender and receiver parameters to retrieve specific details
        return Optional.ofNullable(messageService.getMessagesBySenderAndReceiver(sender, receiver));
    }

    @GetMapping("/api/v1/getMessageBySender")
    public Optional<List<SocketMessage>> getMessagesBySender(@RequestParam UUID senderId){
        Optional<List<SocketMessage>> list = messageService.getSocketMessagesBySender(senderId);
        System.out.println("size "+list.get().size());
        return list;
    }

}
