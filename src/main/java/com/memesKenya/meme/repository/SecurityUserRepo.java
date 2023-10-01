package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface SecurityUserRepo extends JpaRepository<SecurityUser, String> {
//    @Query("SELECT u FROM User u WHERE u.username = :username")
    SecurityUser findByUsername(String username);

    @Query("SELECT s FROM SecurityUser s WHERE s.sub_Id=?1")
    SecurityUser findBySubId(String subId);
}
