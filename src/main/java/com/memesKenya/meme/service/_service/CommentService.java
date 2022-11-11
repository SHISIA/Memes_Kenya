package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    void comment(Comment comment);

    int getTotalPostComments(UUID postId);

    String getCommentContent(Long commentId);

    List<Comment> comments(UUID postId);

    String edit(UUID postId, Long commentId,String newComment);
}