package com.memesKenya.meme.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@MappedSuperclass
public class Post{

    private String postTitle;
    private Timestamp timePosted;
    private int likeCount;
    private int shares;
    private int views;
    private int downloads;
    private double mediaSize;
//    private  String genre;
}
