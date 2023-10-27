package com.memesKenya.meme.configs;

import com.memesKenya.meme.entities.SecurityUser;
import com.memesKenya.meme.service.TokenService;
import com.memesKenya.meme.service._serviceImpls.MemerServiceImpl;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.Session;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig<S extends Session> {
    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    public static final String ipUrl="http://192.168.100.211:3000",
    reactUrl="http://localhost:3000",
            shortIp="http://192.168.100.211:8082",
    currentUrl="http://localhost:8082";

    @Autowired
    @Lazy
    TokenService tokenService;
    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    private final RsaKeyProperties rsaKeys;

    private static final String tokenBasedRememberMeKey="MemesKenya-Key";

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           TokenBasedRememberMeServices rememberMeServices,
                                           MemerServiceImpl userService
                                           ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .headers(headers -> headers
                // allow same origin to frame our site to support iframe SockJS
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                ))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                //allow these requests to pass security
                                new AntPathRequestMatcher("/chat"),
                                new AntPathRequestMatcher("/ws/**"),
                                new AntPathRequestMatcher("/app/chat/**"),
                                new AntPathRequestMatcher("/chat/info"),
                                new AntPathRequestMatcher("/topic/greetings"),
                                new AntPathRequestMatcher("/api/v1/auth/authenticate"),
                                new AntPathRequestMatcher("/api/v1/Memers/newMemer"),
                                new AntPathRequestMatcher("/api/v1/Memers/loggedOut"),
                                new AntPathRequestMatcher("/api/v1/Memers/storeData"),
                                new AntPathRequestMatcher("/api/v1/Memers/logged")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(
                logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/api/v1/Memers/loggedOut")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .oauth2ResourceServer(oauthServer -> oauthServer
                        .jwt(Customizer.withDefaults()))

                .exceptionHandling((ex)-> ex
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .oauth2Login(auth -> auth
                                .successHandler(
                                        (request, response, authentication) ->
                                        {
                                           SecurityUser user= userService.processOAuthPostLogin(request,authentication);
                                            System.out.println("sub Id "+user.getSub_Id());
                                            response.sendRedirect(ipUrl+"/home?"+
                                                    user.getSub_Id() +
                                                            "mmerz"
                                                            +
                                                    getToken(user.getUsername(),user.getSub_Id())
                                            );
                                        }
                                ))
                //remember me settings
                .rememberMe((rememberMe) -> rememberMe
                        .rememberMeServices(rememberMeServices))

                .build();
    }

    private static Cookie getSessionCookie(HttpServletRequest request) {
        HttpSession session = request.getSession();

        // Share the session ID with the React frontend via a cookie
        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true); // Secure this cookie
        sessionCookie.setSecure(true); // Secure this cookie if your app uses HTTPS
        return sessionCookie;
    }


    public String getToken(String username,String password){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                ));
        return tokenService.generateToken(authentication,30, ChronoUnit.SECONDS);
    }

    @Bean
    RememberMeAuthenticationFilter rememberMeFilter(TokenBasedRememberMeServices rememberMeServices,AuthenticationManager authenticationManager) {
        RememberMeAuthenticationFilter rememberMeFilter =
                new RememberMeAuthenticationFilter(authenticationManager,rememberMeServices);
        return rememberMeFilter;
    }

    @Bean
    TokenBasedRememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices("MemesKenya-Key",userDetailsService,
                        TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256);
        return rememberMeServices;
    }

    @Bean
    RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(tokenBasedRememberMeKey);
    }

    @Bean
    public DataSource dataSource(){
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url(jdbcUrl);
            dataSourceBuilder.driverClassName(driverClassName);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(password);
            return dataSourceBuilder.build();
    }

    @Bean
    public UserDetailsService users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(reactUrl,ipUrl,shortIp,currentUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "scrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("sha256", new StandardPasswordEncoder());
        PasswordEncoder passwordEncoder =
                new DelegatingPasswordEncoder(idForEncode, encoders);

        return passwordEncoder;
    }


    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk=new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwkSource=new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
}
