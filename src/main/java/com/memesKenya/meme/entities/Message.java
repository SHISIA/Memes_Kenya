package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Data
@Component
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class Message{
    @Column(
            name = "content",
            nullable = false
    )
    private String message_content;

    private String message_owner;

    private Timestamp timeCreated;
}
