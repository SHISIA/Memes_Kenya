package com.memesKenya.meme.service._service;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaTypeImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {
    MediaTypeImage upload(MultipartFile file) throws Exception;

    MediaTypeImage findPostByUUID(UUID uuid) throws PostNotFoundException;

    int like(UUID uuid) throws Exception;

    void download(UUID postId);

    int likeCount(UUID postId);
}

