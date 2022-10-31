package com.memesKenya.meme.service._service;

import com.memesKenya.meme.entities.Memer;

import java.util.List;

public interface AdminService {

    int getAllNumberOfMemers();

    Memer getMemerByNickName(String memerNickName);

    List<Memer> getMemerByFirstName(String memerFirstName);

    List<Memer> getMemerBySecondName(String memerSecondName);

    List<Memer> getMemerByBirthMonth(String birthMonth);

    List<Memer> getMemerByBirthYear(String birthYear);

    List<Memer> getMemerByBirthDay(String birthDay);
}
