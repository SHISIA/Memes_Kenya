package com.memesKenya.meme.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Component
@Getter
@Setter
public class Notification {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID memerId;

    private String notification;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Memer.class)
    @JoinColumn(name = "Notified_Memer",nullable = false,referencedColumnName = "user_Id")
    private Memer person;

}
