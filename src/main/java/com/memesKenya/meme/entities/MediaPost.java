package com.memesKenya.meme.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.memesKenya.meme.model.Post;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Entity
@Table(
        name = "Post"
)
public class MediaPost extends Post {
    @Id
    @Column(
            name = "post_Id",
            nullable = false,
            unique = true
    )
    @GeneratedValue (generator = "UUID",strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid",strategy = "org.hibernate.id.UUIDGenerator")
//    @Type(type = "org.hibernate.type.UUIDCharType")
    private  UUID postId;

    private String imageType;

    @Lob
    private byte[] imageData;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_Id",unique = true,referencedColumnName = "user_Id")
    @JsonBackReference
    private  Memer memer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    private List<Comment> comments;

    public MediaPost(String title, double mediaSize, String imageType, byte[] imageData,Memer owner){
        super(title,Timestamp.from(Instant.now()),0,0,0,0,mediaSize);
        this.imageType=imageType;
        this.imageData=imageData;
        this.memer=owner;
    }

}
