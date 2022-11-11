package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {

    @Query("select count(c) from Comment c where c.post=?1 ")
    int getTotalPostComments(UUID postId);

    @Query("select c from Comment c where c.post=?1 ")
    List<Comment> getPostComments(UUID postId);

    @Query("select c.message_content from Comment c where c.id=?1 ")
    String getCommentContent(Long commentId);
    @Query("update Comment c set c.message_content=?3 where c.id=?2 and c.post=?1 ")
    void editComment(UUID postId, Long commentId, String newContents);
}
