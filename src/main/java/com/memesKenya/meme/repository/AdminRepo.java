package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepo extends JpaRepository<Admin, UUID> {
}
