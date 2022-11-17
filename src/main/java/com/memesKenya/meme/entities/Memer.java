package com.memesKenya.meme.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.memesKenya.meme.model.User;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Component
@Table(
        name = "meme_member",
        uniqueConstraints =
                { @UniqueConstraint
                        (name = "UniqueContactAndNickName",
                                columnNames = { "phone_number", "nick_name" })
                })
public class Memer extends User {
   @Column(
           name = "phone_number",
           nullable = false
   )
   private String phoneNumber;

   @Column(
           name = "nick_name",
           nullable = false
   )
   private String nickName;

   @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
   private List<Comment> comments;

   @JsonManagedReference(value = "sender")
   @OneToMany(cascade = CascadeType.ALL,mappedBy = "sender",fetch = FetchType.LAZY)
   private List<Message> messages;

   @JsonManagedReference(value = "recipient")
   @OneToMany(cascade = CascadeType.ALL,mappedBy = "recipient",fetch = FetchType.LAZY)
   private List<Message> received_messages;

   @OneToMany(fetch = FetchType.LAZY,mappedBy = "memer")
   @ToString.Exclude
   private List<MediaPost> posts;

   @ManyToOne(fetch = FetchType.LAZY,targetEntity = ChatRoom.class)
   private ChatRoom chat;

   @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   @ToString.Exclude
   private List<Notification> notifications;

   @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "memer")
   @ToString.Exclude
   private List<AdminMessage> adminMessages;

   public Memer(String username,String password,byte[] userAvatar,String emailAddress,
                String firstName,String lastName,String nickName,String phoneNumber){
      super(username,password,userAvatar,emailAddress,Timestamp.from(Instant.now()),firstName,lastName);
      this.nickName=nickName;
      this.phoneNumber=phoneNumber;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      Memer memer = (Memer) o;
      return nickName != null && Objects.equals(nickName, memer.nickName);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }
}
