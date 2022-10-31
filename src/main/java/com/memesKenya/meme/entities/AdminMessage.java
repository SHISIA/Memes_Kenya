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

    @Column(
            name = "content",
            nullable = false
    )
    private String message_content;

    private String adminName;

    private Timestamp timeCreated;
}
