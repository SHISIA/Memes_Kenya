package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.model.MessageModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    List<Message> getMessagesByNickName(String nickName);

    String sendMessage(MessageModel message);

    Optional<List<Message>> getMessageBySenderAndReceiver(UUID sender,UUID receiver);

    Optional<List<Message>> getMessagesBySender(UUID sender);

    List<Message> getMessageContaining(String content, String nickName);

    boolean deleteMessage(UUID sender,UUID receiver);
}
