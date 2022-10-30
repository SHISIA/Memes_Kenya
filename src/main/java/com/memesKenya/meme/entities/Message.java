package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Data
@Component
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class Message{
    private String messageOwner;
    private String message_content;
    private Timestamp timeCreated;
}
