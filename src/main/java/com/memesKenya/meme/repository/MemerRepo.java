package com.memesKenya.meme.repository;

import com.memesKenya.meme.entities.Memer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemerRepo extends JpaRepository<Memer, UUID> {
}
