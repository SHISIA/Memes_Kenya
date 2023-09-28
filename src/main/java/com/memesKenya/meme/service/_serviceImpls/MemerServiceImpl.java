package com.memesKenya.meme.service._serviceImpls;

import com.memesKenya.meme.controller.MemerController;
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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
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
        String email = "",picture="",first_name="",sub="",last_name="",token="";
        Provider provider=GOOGLE;
        if (request.getRequestURI().endsWith("google")){
            oauthUser = (DefaultOidcUser) authentication.getPrincipal();
            //currently this works for google
            email = oauthUser.getAttribute("email");
            picture = oauthUser.getAttribute("picture");
            first_name = oauthUser.getAttribute("given_name");
            last_name = oauthUser.getAttribute("family_name");
            sub = oauthUser.getAttribute("sub");
//           Google uses DefaultOidcUser
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

        //find out if the user exists
        SecurityUser existUser=securityUserRepo.findByUsername(email);
        //create a new user since he/she doesn't exist
        if (existUser == null) {
            //create a new memer
            Memer memer = new Memer(email,passwordEncoder.encode(sub),
                    //convert image to a byte array for a database
                    ImageToByteArrayConvertor.convert(picture),
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
            //authenticate after signing in using Oauth 2.0
//            response.sendRedirect("/api/v1/auth/logged");
        }
        System.out.println(" token "+token);
        response.sendRedirect("/api/v1/auth/logged");

    }

    public String getToken(String username,String password){
        System.out.println("username "+username+"password "+password);
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                ));
       String token= tokenService.generateToken(authentication);
        System.out.println("Token Successfully sent!!");
        return token;
    }

    public String sendToken(String webToken) {
        try {
            // Create HttpHeaders with the Content-Type header and Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + webToken);

            // Create a request entity with the token as the payload and headers
            HttpEntity<String> requestEntity = new HttpEntity<>(webToken, headers);

            // Make an HTTP POST request to send the token to the API
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Handle the API's response if needed
            String response = responseEntity.getBody();
            getToken(webToken);
            return response;
        } catch (HttpClientErrorException.Unauthorized e) {
            // Handle the 401 Unauthorized error here
            e.printStackTrace();
            return "Authentication error";
        }
    }

    public String getToken(String webToken){
        try {
            // Build the API endpoint URL with query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(loggedUrl);
                    // Add query parameters if needed
//                    .queryParam("param1", "value1");
            System.out.println("did iid ijee");
            // Create HttpHeaders with the Authorization header
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + webToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println("oijhvbwefwf");
            // Make a GET request using getForEntity
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class);

            // Handle the API's response if needed
            return responseEntity.getBody();
        } catch (Exception e) {
            // Handle errors here
            e.printStackTrace();
            return "Error occurred";
        }
    }

}
