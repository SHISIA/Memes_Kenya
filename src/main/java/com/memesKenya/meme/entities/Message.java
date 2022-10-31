package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@Entity
@Table(
        name = "Messages"
)
public class Message{
    @Id
    @Column(
            name = "unique_msgId",
            nullable = false
    )
    private UUID messageId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "message_owner")
    private Memer user;

    @Column(
            name = "content",
            nullable = false
    )
    private String message_content;

    private String message_owner;

    private Timestamp timeCreated;
}