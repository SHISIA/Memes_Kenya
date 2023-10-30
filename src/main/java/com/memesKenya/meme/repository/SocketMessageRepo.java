package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.Message;
import com.memesKenya.meme.messaging.SocketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SocketMessageRepo  extends JpaRepository<SocketMessage, UUID> {

    //Get messages from latest to earliest
    @Query("SELECT m FROM SocketMessage m WHERE (m.senderId = :sender AND m.receiverId = :receiver) OR (m.senderId = :receiver AND m.receiverId = :sender)" +
            "ORDER BY m.timestamp ASC")
    List<SocketMessage> getMessagesBySenderAndReceiver(@Param("sender") UUID sender, @Param("receiver")UUID receiver);

    @Modifying
    @Query("DELETE FROM SocketMessage WHERE senderId = :sender AND receiverId =:receiver")
    void deleteSocketMessage(@Param("sender")UUID sender,@Param("receiver") UUID receiver);

    @Query("SELECT m FROM SocketMessage m where m.senderId=?1 OR m.receiverId=?1 ORDER BY m.timestamp DESC")
    Optional<List<SocketMessage>> getSocketMessagesBySender(UUID senderId);
}
