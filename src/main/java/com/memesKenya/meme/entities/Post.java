package com.memesKenya.meme.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Post{
    private String postOwner;
    private String postTitle;
    private String postFilePath;
    private Timestamp durationSincePost;
    private int likeCount;
    private int shares;
    private int views;
    private int downloads;
    private double mediaSize;
    private double maxSize;
}
