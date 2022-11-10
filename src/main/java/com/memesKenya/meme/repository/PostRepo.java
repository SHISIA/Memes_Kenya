package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.MediaPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<MediaPost, UUID> {

    @Modifying
    @Query("update MediaPost m set m.likeCount=m.likeCount+1 where m.postId=?1 ")
    void like(UUID id);

    @Modifying
    @Query("update MediaPost m set m.shares=m.shares+1 where m.postId=?1 ")
    void share(UUID id);

    @Modifying
    @Query("update MediaPost m set m.downloads=m.downloads+1 where m.postId=?1 ")
    void download(UUID post);

    @Query("select m.shares from MediaPost m where m.postId=?1")
    int getShareCount(UUID postId);

    @Query("select m.likeCount from MediaPost m where m.postId=?1")
    int getLikeCount(UUID post);
}
