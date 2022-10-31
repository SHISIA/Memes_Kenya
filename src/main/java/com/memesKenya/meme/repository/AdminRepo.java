package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepo extends JpaRepository<Admin, UUID> {
    void findByUserName(String userName);
}
