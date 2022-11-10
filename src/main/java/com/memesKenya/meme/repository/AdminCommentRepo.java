package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.AdminComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminCommentRepo extends JpaRepository<AdminComment, Long> {
}
