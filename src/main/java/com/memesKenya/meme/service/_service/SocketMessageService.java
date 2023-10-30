package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.messaging.SocketMessage;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SocketMessageService {
    List<SocketMessage> getMessagesBySenderAndReceiver(UUID sender,UUID receiver);

    SocketMessage sendMessage(SocketMessage socketMessage);

    boolean deleteSocketMessage(UUID sender, UUID receiver);

    Optional<List<SocketMessage>> getSocketMessagesBySender(UUID senderId);
}
