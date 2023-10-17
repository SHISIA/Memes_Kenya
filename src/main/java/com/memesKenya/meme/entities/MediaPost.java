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
    @GenericGenerator(name = "uuid")
    private  UUID postId;

    private String imageType;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_Id",referencedColumnName = "user_Id")
    @JsonBackReference
    private  Memer memer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")
    private List<Comment> comments;

    public MediaPost(String title, double mediaSize, String imageType, byte[] imageData,Memer owner,String nickName){
        super(title,Timestamp.from(Instant.now()),0,0,0,0,mediaSize,nickName);
        this.imageType=imageType;
        this.imageData=imageData;
        this.memer=owner;
    }

}
