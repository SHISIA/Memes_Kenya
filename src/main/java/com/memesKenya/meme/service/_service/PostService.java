package com.memesKenya.meme.service._service;

import com.memesKenya.meme.Exceptions.PostNotFoundException;
import com.memesKenya.meme.entities.MediaPost;
import com.memesKenya.meme.entities.Memer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PostService {
    void upload(MultipartFile file,String userId,String description,String nickName) throws Exception;

    MediaPost findPostByUUID(UUID postId) throws PostNotFoundException;

    int like(UUID postId) throws Exception;

    void download(UUID postId);

    void share(UUID postId);

    int getShareCount(UUID postId);

    int likeCount(UUID postId);
    Memer postOwner(UUID postOwner,UUID postId);

    int unLike(UUID postId) throws Exception;

    Page<MediaPost> getPosts(PageRequest pageRequest);

    int getDownloads(UUID postId);
}

