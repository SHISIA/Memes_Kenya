package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.Comment;
import com.memesKenya.meme.repository.CommentRepo;
import com.memesKenya.meme.service._service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo repo;
    @Override
    public void comment(Comment comment) {
        repo.save(comment);
    }

    @Override
    public int getTotalPostComments(UUID postId) {
        return repo.getTotalPostComments(postId);
    }

    @Override
    public String getCommentContent(UUID commentId) {
        return repo.getCommentContent(commentId);
    }

    @Override
    public List<Comment> comments(UUID postId) {
        return repo.getPostComments(postId);
    }

    @Transactional
    @Override
    public String edit(UUID postId, UUID commentId,String newComment) {
         repo.editComment(postId,commentId,newComment);
         return "Edit Successful";
    }
}
