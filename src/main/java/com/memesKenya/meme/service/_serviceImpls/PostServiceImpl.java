package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.Post;
import com.memesKenya.meme.service._service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public List<Post> getPostContaining(String term) {
        return null;
    }

    @Override
    public List<Post> getLatestPost() {
        return null;
    }

    @Override
    public void flagPost(Post post) {

    }

    @Override
    public void uploadPost() {

    }

    @Override
    public void createPost() {

    }
}
