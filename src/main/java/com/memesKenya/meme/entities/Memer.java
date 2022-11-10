package com.memesKenya.meme.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
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
public class Memer extends User{
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

   @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
   private List<Message> messages;

   @OneToMany(fetch = FetchType.LAZY,mappedBy = "memer")
   @ToString.Exclude
   private List<MediaPost> images;

   @ManyToOne(fetch = FetchType.LAZY)
   private ChatRoom chat;
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
