package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.MediaPost;
import com.memesKenya.meme.entities.Memer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface PostRepo extends JpaRepository<MediaPost, UUID> {

    @Modifying
    @Query("UPDATE MediaPost m SET m.likeCount=m.likeCount+1 WHERE m.postId=?1 ")
    void like(UUID id);

    @Modifying
    @Query("UPDATE MediaPost m SET m.shares=m.shares+1 WHERE m.postId=?1 ")
    void share(UUID id);

    @Modifying
    @Query("UPDATE MediaPost m SET m.downloads=m.downloads+1 WHERE m.postId=?1 ")
    void download(UUID post);

    @Query("SELECT m.shares FROM MediaPost m WHERE m.postId=?1")
    int getShareCount(UUID postId);

    @Query("SELECT m.likeCount FROM MediaPost m WHERE m.postId=?1")
    int getLikeCount(UUID post);

    @Query("SELECT memer FROM Memer memer WHERE memer.userId IN (SELECT m.memer FROM MediaPost m WHERE m.memer=?1 AND m.postId=?2)")
    Memer postOwner(UUID postOwner, UUID postId);

    @Modifying
    @Query("UPDATE MediaPost m SET m.likeCount=m.likeCount-1 WHERE m.postId=?1 ")
    int unLike(UUID postID);

    @Query("SELECT m.downloads FROM MediaPost m WHERE m.postId=?1 ")
    int getDownloads(UUID postId);
}
