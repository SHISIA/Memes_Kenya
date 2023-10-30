package com.memesKenya.meme.messaging;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class SocketMessage {
    @Id
    private UUID messageId;
    private String senderName;
    private UUID senderId;
    private UUID receiverId;
    private String receiverName;
    private String message;
    private Status status;
    private Date timestamp;
}
