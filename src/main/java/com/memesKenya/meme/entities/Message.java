package com.memesKenya.meme.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "Messages"
)
public class Message{
    @Id
    @GeneratedValue (generator = "UUID",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "unique_msgId",
            nullable = false,
            unique = true
    )
    private UUID messageId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "message_sender",referencedColumnName = "user_Id")
    private Memer sender;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinColumn(name = "message_recipient",referencedColumnName = "user_Id")
    private Memer recipient;

    @Column(
            name = "Message_Content",
            nullable = false
    )
    private String message_content;

    private Timestamp timeCreated;

    public Message(Memer sender,Memer recipient,String message_content,Timestamp timeCreated){
       this.message_content=message_content;
       this.timeCreated=timeCreated;
       this.recipient=recipient;
       this.sender=sender;
    }
}
