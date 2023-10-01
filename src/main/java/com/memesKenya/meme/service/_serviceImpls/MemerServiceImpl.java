package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.entities.AccountStatus;
import com.memesKenya.meme.entities.Authorities;
import com.memesKenya.meme.entities.Memer;
import com.memesKenya.meme.entities.SecurityUser;
import com.memesKenya.meme.model.ImageToByteArrayConvertor;
import com.memesKenya.meme.model.Person;
import com.memesKenya.meme.model.Provider;
import com.memesKenya.meme.repository.AuthoritiesRepo;
import com.memesKenya.meme.repository.MemerRepo;
import com.memesKenya.meme.repository.SecurityUserRepo;
import com.memesKenya.meme.service._service.MemerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.memesKenya.meme.model.Provider.*;

@Service
public class MemerServiceImpl implements MemerService {

    @Value("${auth.token.api.url}")
    private String apiUrl;

    @Value("${auth.logged.api.url}")
    private String loggedUrl;
    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private AuthoritiesRepo authoritiesRepo;
    @Autowired
    private SecurityUserRepo securityUserRepo;
    @Autowired
    private MemerRepo repo;
    private final PasswordEncoder passwordEncoder;
    private Memer memer;
    private SecurityUser user;
    @Autowired
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
         user=new SecurityUser(person.getUserName(), passwordEncoder.encode(person.getUserPassword()),Provider.GOOGLE,
                1,"NON-OATH2.0", authorities,memer
         );

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
    public SecurityUser findBySubId(@NonNull String subId) {
        if (!subId.isBlank()){
            return securityUserRepo.findBySubId(subId);
        }
        return null;
    }

    @Override
    @Transactional
    public String changeMemerAvatar(Memer memer,String avatar) {
         repo.changeMemerAvatar(memer.getUserId(),avatar);
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

    public SecurityUser processOAuthPostLogin(HttpServletRequest request, Authentication authentication) throws IOException {
        OAuth2User oauthUser;
        String email = "",picture="",first_name="",sub="",last_name="";
        Provider provider=GOOGLE;
        if (request.getRequestURI().endsWith("google")){
            oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            //currently this works for google
            email = oauthUser.getAttribute("email");
            picture = oauthUser.getAttribute("picture");
            first_name = oauthUser.getAttribute("given_name");
            last_name = oauthUser.getAttribute("family_name");
            sub = oauthUser.getAttribute("sub");
            //Google uses DefaultOidcUser
        }else if (request.getRequestURI().endsWith("github")){
            //GitHub uses OAuth2User
            provider=GITHUB;
            oauthUser = (OAuth2User) authentication.getPrincipal();
            //currently this works for google
            email = oauthUser.getAttribute("email");
            picture = oauthUser.getAttribute("avatar_url");
            first_name = oauthUser.getAttribute("login");
            last_name = oauthUser.getAttribute("url");
            sub = Objects.requireNonNull(oauthUser.getAttribute("id")).toString();
            System.out.println("Email "+email+" Picture "+picture+" first_name "+first_name
            +" last_name "+last_name+ " sub "+sub);
        }
        else if (request.getRequestURI().endsWith("linkedin")){
            provider=LINKEDIN;
//           GitHub uses OAuth2User
            oauthUser = (OAuth2User) authentication.getPrincipal();
        }else{
            System.out.println("extra");
        }
        SecurityUser user;

        //find out if the user exists
        SecurityUser existUser=securityUserRepo.findByUsername(email);
        //create a new user since he/she doesn't exist
        if (existUser == null) {
            //create a new memer
            Memer memer = new Memer(email,passwordEncoder.encode(sub),
                    //convert image to a byte array for a database
                    picture,
                    email,first_name,last_name,"@"+last_name,
                    null, AccountStatus.ACTIVE.name());
            //create a new security user for Spring Security login purposes (Login table)
            SecurityUser newUser = new SecurityUser();
            newUser.setUsername(email);
            newUser.setPassword(passwordEncoder.encode(sub));
            newUser.setProvider(provider);
            newUser.setSub_Id(sub);
            newUser.setAuthorities(new Authorities(email,"ROLE_USER",newUser));
            newUser.setMemer(memer);
            newUser.setEnabled(1);
            securityUserRepo.save(newUser);
            user=newUser;
        }else {
            return existUser;
        }
        return user;
    }

}
