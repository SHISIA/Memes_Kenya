package com.memesKenya.meme.service;

import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.repository.MemerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetail implements UserDetailsService {
    @Autowired
    private MemerRepo memerRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Memer user=memerRepo.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("User not exists by Username");
        }
        System.out.println("User Details "+username+" "+user.getNickName()+" "+user.getPhoneNumber()
        +user.getAccountStatus()+" "+user.getSecondName()+" \nID "+user.getUserId());
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(username,user.getUserPassword(),authorities);
    }
}
