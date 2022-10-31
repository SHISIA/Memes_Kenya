package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Memer;

import java.util.List;

public interface MemerService {

    List<Memer> getAllMemers();

    boolean registerNewMemer(Memer memer);

    List<Memer> getMemerByAnyName(String memerAnyName);

}
