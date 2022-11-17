package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {
    List<Message> findBySender(UUID userId);

    @Modifying
    @Query("UPDATE Message m SET m.sender=?1,m.recipient=?2 WHERE m.messageId=?3")
    void updateSenderAndReceiver(UUID sender, UUID recipient,UUID messageId);
    @Query("SELECT m FROM Message m WHERE m.sender=?1 and m.recipient=?2")
    List<Message> findBySender(UUID sender,UUID receiver);

    @Query("SELECT m FROM Message m WHERE (m.message_content LIKE '%?1%' AND (m.recipient=?2 OR m.sender=?2))")
    List<Message> findMessageContaining(String content,UUID memer);
}
