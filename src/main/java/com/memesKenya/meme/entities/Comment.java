package com.memesKenya.meme.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Component
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "Comments"
)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "uuid",strategy = "org.hibernate.id.UUIDGenerator")
//    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "postId",nullable = false,referencedColumnName = "post_Id")
    private MediaPost post;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "commentOwner",nullable = false,referencedColumnName = "user_Id")
    private Memer user;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "AdminCommentOwner",nullable = false,referencedColumnName = "user_Id")
    private Admin admin;

    @Column(
            name = "Contents",
            nullable = false
    )
    private String message_content;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Timestamp timeCreated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return id != null && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
