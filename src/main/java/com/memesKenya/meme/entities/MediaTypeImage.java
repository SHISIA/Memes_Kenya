package com.memesKenya.meme.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Entity
@Table(
        name = "ImagePost"
)
public class MediaTypeImage extends Post{
    @Id
    @Column(
            name = "post_Id",
            nullable = false,
            unique = true
    )
    @GeneratedValue (generator = "UUID")
    @GenericGenerator(name = "uuid",strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private  UUID postId;

    private String imageType;

    @Lob
    private byte[] imageData;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "ImagePostOwner"
    )
    private  Memer memer;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "image")
    private List<Comment> comments;

    public MediaTypeImage(String title, double mediaSize, String imageType, byte[] imageData){
        super(title,Timestamp.from(Instant.now()),0,0,0,0,mediaSize);
        this.imageType=imageType;
        this.imageData=imageData;
    }

}
