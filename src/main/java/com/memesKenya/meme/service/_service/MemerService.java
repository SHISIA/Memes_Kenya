package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.SecurityUser;
import com.memesKenya.meme.model.Person;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MemerService {

    List<Memer> getAllMemers();

    boolean registerNewMemer(Person memer);

    Memer getMemerByNickName(String memerAnyNameOrPhone);

    String changeMemerAvatar(Memer memer,String avatar) throws IOException;

    Memer findById(UUID memerId);

    Memer getMemerByAnyName(String nameOrPhone);

     SecurityUser findBySubId(String subId);

    Memer findByUsername(String username);
}
