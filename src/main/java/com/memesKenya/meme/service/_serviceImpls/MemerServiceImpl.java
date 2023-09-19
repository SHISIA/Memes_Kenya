package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.Authorities;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.SecurityUser;
import com.memesKenya.meme.model.Person;
import com.memesKenya.meme.repository.AuthoritiesRepo;
import com.memesKenya.meme.repository.MemerRepo;
import com.memesKenya.meme.repository.SecurityUserRepo;
import com.memesKenya.meme.service._service.MemerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemerServiceImpl implements MemerService {
    @Autowired
    private AuthoritiesRepo authoritiesRepo;
    @Autowired
    private SecurityUserRepo securityUserRepo;
    @Autowired
    private MemerRepo repo;
    private final PasswordEncoder passwordEncoder;

    private Memer memer;
    private SecurityUser user;
    private Authorities authorities;

    public MemerServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Memer> getAllMemers() {
        return repo.findAll();
    }

    @Override
    public boolean registerNewMemer(Person person) {
         memer=new Memer(person.getUserName(),
                passwordEncoder.encode(person.getUserPassword()),
                null, person.getEmailAddress(),
                person.getFirstName(), person.getSecondName(),
                "@"+person.getNickName(), person.getPhoneNumber(),
                person.getAccountStatus());
         authorities=new Authorities(person.getUserName(),"ROLE_USER",user);
         user=new SecurityUser(person.getUserName(), passwordEncoder.encode(person.getUserPassword()),
                1,authorities,memer);

        Optional<Memer> memerOptional=Optional.ofNullable(repo.findByNickName(person.getNickName()));
        if (memerOptional.isEmpty()){
            repo.save(memer);
            authoritiesRepo.save(authorities);
            securityUserRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Memer getMemerByNickName(@NonNull String memerNickName) {
        if (!memerNickName.isBlank()){
            return repo.findByNickName(memerNickName);
        }
        return null;
    }

    @Override
    @Transactional
    public String changeMemerAvatar(Memer memer,MultipartFile avatar) throws IOException {
        if (!(Objects.equals(avatar.getContentType(), "image/png") ||Objects.equals(avatar.getContentType(), "image/jpeg"))){
            return "Cannot use "+avatar.getContentType()+" for your picture handle";
        }
         repo.changeMemerAvatar(memer.getUserId(),avatar.getBytes());
         return "Updated "+memer.getNickName()+"'s Profile pic Successfully";
    }

    @Override
    public Memer findById(UUID memerId) {
        return repo.findById(memerId).get();
    }

    @Override
    public Memer getMemerByAnyName(String name) {
        return repo.findByAnyName(name);
    }
}
