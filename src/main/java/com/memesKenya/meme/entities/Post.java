package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@MappedSuperclass
public class Post{
    @Id
    @Column(
            name = "post_Id",
            nullable = false,
            unique = true
    )
    private UUID postId;

    @Column(name = "owner")
    private String postOwner;
    private String postTitle;
    private String postFilePath;
    private Timestamp timePosted;
    private int likeCount;
    private int shares;
    private int views;
    private int downloads;
    private double mediaSize;
}
