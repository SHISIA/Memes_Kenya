package com.memesKenya.meme.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

@Component
@Getter
@Setter
@Entity
public class Notification {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = GenerationType.AUTO,generator ="uuid")
    private UUID notificationId;

    private String notification;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Memer.class)
    @JoinColumn(name = "Notified_Memer",nullable = false,referencedColumnName = "user_Id")
    private Memer person;

}
