package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MemerService {

    List<Memer> getAllMemers();

    boolean registerNewMemer(Person memer);

    Memer getMemerByNickName(String memerAnyNameOrPhone);

    String changeMemerAvatar(Memer memer,MultipartFile avatar) throws IOException;

    Memer findById(UUID memerId);

    Memer getMemerByAnyName(String nameOrPhone);
}
