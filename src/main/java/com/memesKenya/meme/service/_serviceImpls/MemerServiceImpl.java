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
import com.memesKenya.meme.service.TokenService;
import com.memesKenya.meme.service._service.MemerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.memesKenya.meme.model.Provider.*;

@Service
public class MemerServiceImpl implements MemerService {
    @Autowired
    private AuthoritiesRepo authoritiesRepo;
    @Autowired
    private SecurityUserRepo securityUserRepo;
    @Autowired
    private MemerRepo repo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    private Memer memer;
    private SecurityUser user;

    @Autowired
    private AuthenticationManager authenticationManager;
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
                1
                 , authorities,memer
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

    public void processOAuthPostLogin(HttpServletResponse response,
                                      HttpServletRequest request, Authentication authentication) throws IOException {

        OAuth2User oauthUser = null;
        Provider provider=GOOGLE;
        if (request.getRequestURI().endsWith("google")){
//           Google uses DefaultOidcUser
            oauthUser = (DefaultOidcUser) authentication.getPrincipal();
        }else if (request.getRequestURI().endsWith("github")){
            provider=GITHUB;
//           GitHub uses OAuth2User
            oauthUser = (OAuth2User) authentication.getPrincipal();
        }
        else if (request.getRequestURI().endsWith("linkedin")){
            provider=LINKEDIN;
//           GitHub uses OAuth2User
            oauthUser = (OAuth2User) authentication.getPrincipal();
        }else{
            System.out.println("extra");
        }
        //currently this works for google
        String email = oauthUser.getAttribute("email");
        String picture = oauthUser.getAttribute("picture");
        String first_name = oauthUser.getAttribute("given_name");
        String last_name = oauthUser.getAttribute("family_name");
        System.out.println("Oauth "+email+" picture "+picture+" last name "+
                last_name+" first_name "+first_name);
        SecurityUser existUser=securityUserRepo.findByUsername(email);

        if (existUser == null) {
            //generate a number from 0 to 20 (to be appended to the password for uniqueness)
            int randInt = (int) (Math.random() * 20000);
            //create a new memer
            Memer memer = new Memer(email,passwordEncoder.encode(first_name+last_name+randInt),
                    //convert image to a byte array for a database
                    ImageToByteArrayConvertor.convert(picture),
                    email,first_name,last_name,"@"+last_name,
                    null, AccountStatus.ACTIVE.name());
            //create a new security user for Spring Security login purposes (Login table)
            SecurityUser newUser = new SecurityUser();
            newUser.setUsername(email);
            newUser.setPassword(passwordEncoder.encode(first_name+last_name+randInt));
            newUser.setProvider(provider);
            newUser.setAuthorities(new Authorities(email,"ROLE_USER",newUser));
            newUser.setMemer(memer);
            newUser.setEnabled(1);
            securityUserRepo.save(newUser);
            //authenticate after signing in using Oauth 2.0
            authenticate(newUser.getUsername(),newUser.getPassword());
        }else {
            System.out.println("User "+existUser.getUsername()+" is a Memer");
        }

        response.sendRedirect("/api/v1/Memers/logged");
    }

    public void authenticate(String username,String password){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                ));
       String token= tokenService.generateToken(authentication);
        System.out.println("Token: "+token);
    }
}
