package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Post{
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
