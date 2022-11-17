package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.model.MessageModel;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<Message> getMessagesByNickName(String nickName);

    String sendMessage(MessageModel message);

    List<Message> getMessagesBySender(String nickName,String receiver);

    List<Message> getMessageContaining(String content, String nickName);
}
