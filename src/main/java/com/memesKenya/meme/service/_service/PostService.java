package com.memesKenya.meme.service._service;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaPost;
import com.memesKenya.meme.entities.Memer;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PostService {
    MediaPost upload(MultipartFile file) throws Exception;

    MediaPost findPostByUUID(UUID postId) throws PostNotFoundException;

    int like(UUID postId) throws Exception;

    void download(UUID postId);

    void share(UUID postId);

    int getShareCount(UUID postId);

    int likeCount(UUID postId);
    Memer postOwner(UUID postOwner,UUID postId);
}

