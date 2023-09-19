package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityUserRepo extends JpaRepository<SecurityUser, String> {
}
