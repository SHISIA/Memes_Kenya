package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.HashMap;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatRoom {

    @Autowired
    private User owner;
    @Autowired
    private User guest;

    private String roomID;

    private HashMap<User,Message> roomMessages;
}
