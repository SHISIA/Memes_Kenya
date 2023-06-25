package com.memesKenya.meme.controller;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.service._service.MemerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class MemerControllerTest {

    @Mock private MemerService service;
    private MemerController underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest=new MemerController();
    }
    @Test
    void allMemers() {
        //Given

        //When
        List<Memer> actual = underTest.getAllMemers();

        //Then

    }

    @Test
    void newMemerAccount() {
    }

    @Test
    void changeAvatar() {
    }

    @Test
    void viewAvatar() {
    }

    @Test
    void getByNickName() {
    }

    @Test
    void getByAnyName() {
    }
}