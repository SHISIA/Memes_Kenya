package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Memer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface MemerRepo extends JpaRepository<Memer, UUID> {
    @Query("select m from Memer m where m.nickName=?1")
    Memer findByNickName(String memerNickName);


    @Modifying
    @Query("UPDATE Memer m SET m.userAvatar=?2 WHERE m.userId=?1")
    void changeMemerAvatar(UUID memerId,byte[] avatar);

    @Query("SELECT m FROM Memer m WHERE m.userName=?1 OR m.firstName=?1 OR m.secondName=?1")
    Memer findByAnyName(String name);

    @Query("SELECT m FROM Memer m WHERE m.userName=?1")
    Memer findByUserName(String name);
}
