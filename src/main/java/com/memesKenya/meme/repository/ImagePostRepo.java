package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.MediaTypeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImagePostRepo extends JpaRepository<MediaTypeImage, UUID> {

    @Modifying
    @Query("update MediaTypeImage m set m.likeCount=m.likeCount+1 where m.postId=id ")
    void like(@Param("id") UUID id);

    @Modifying
    @Query("update MediaTypeImage m set m.downloads=m.downloads+1 where m.postId=id ")
    void download(@Param("id") UUID post);

    @Query("select m.likeCount from MediaTypeImage m where m.postId=?1")
    int getLikeCount(UUID post);
}
