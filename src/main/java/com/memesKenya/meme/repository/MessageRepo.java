package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {

    @Modifying
    @Query("DELETE FROM Message WHERE sender = :sender AND recipient =:receiver")
    void deleteMessage(@Param("sender") Memer sender, @Param("receiver")Memer receiver);

    @Query("SELECT m FROM Message m WHERE m.sender = :sender AND m.recipient =:receiver")
    List<Message> getMessages(@Param("sender") Memer sender, @Param("receiver")Memer receiver);

    @Query("SELECT m FROM Message m where m.sender=?1 OR m.recipient=?1")
    List<Message> findBySender(Memer memerUUID);
}
