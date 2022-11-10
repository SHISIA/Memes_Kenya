package com.memesKenya.meme.service._service;

import java.util.UUID;

public interface CommentService {

    void comment(UUID commentId);

    int getTotalPostComments(UUID postId);

    String getCommentContents(UUID commentId);
}
