package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Post;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    List<Post> getPostContaining(String term);

    List<Post> getLatestPost();

    void flagPost(Post post);

    void uploadPost();

    void createPost();
}
