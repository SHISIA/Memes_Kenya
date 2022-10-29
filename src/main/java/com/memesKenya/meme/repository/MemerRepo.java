package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Memer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemerRepo extends JpaRepository<Memer, UUID> {
}
