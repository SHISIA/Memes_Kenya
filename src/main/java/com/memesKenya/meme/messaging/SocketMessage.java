package com.memesKenya.meme.messaging;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SocketMessage {
    private String senderName;
    private String receiverName;
    private String message;
    private Status status;
    private Date timestamp;
}
