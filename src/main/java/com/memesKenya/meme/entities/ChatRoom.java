package com.memesKenya.meme.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ChatRoom {
    @Id
    @Column(name = "room_id", nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID roomId;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chat")
    private List<Memer> memers;


    private HashMap<User,Message> roomMessages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return roomId != null && Objects.equals(roomId, chatRoom.roomId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
