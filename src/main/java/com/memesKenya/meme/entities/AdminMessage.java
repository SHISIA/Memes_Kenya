package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@Entity
@Table(
        name = "AdminTexts"
)
public class AdminMessage {
    @Id
    @Column(
            name = "unique_adminId",
            nullable = false
    )
    private UUID messageId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "adminName")
    private Admin admin;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient")
    private Memer memer;

    @Column(
            name = "Message",
            nullable = false
    )
    private String message_content;

    private Timestamp timeCreated;
}
