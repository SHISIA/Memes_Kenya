package com.memesKenya.meme.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix="rsa")
//@PropertySource("classpath:application.properties")
public record RsaKeyProperties(RSAPublicKey publicKey,RSAPrivateKey privateKey){
}
