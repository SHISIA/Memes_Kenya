package com.memesKenya.meme.model;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@RequiredArgsConstructor
public class ResponseData {
    private UUID postId;
    private String fileName;
    private String downloadURL;
    private String fileType;
    private String owner;
    private String fileSize;
    private Timestamp postedTime;

    public ResponseData(String postId, String postTitle, String downloadURL, String imageType, String valueOf, String timePosted) {
    }
}
