package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}
