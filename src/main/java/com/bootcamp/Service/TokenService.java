package com.bootcamp.Service;

import com.bootcamp.Entities.User.Token;
import com.bootcamp.Entities.User.User;
import com.bootcamp.Exceptions.EmailExistsException;
import com.bootcamp.Exceptions.TokenExpiredException;
import com.bootcamp.Exceptions.TokenNotFoundException;
import com.bootcamp.Repository.TokenRepository;
import com.bootcamp.Repository.UserRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Service
public class TokenService{
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");


    @Autowired
    TokenRepository tokenRepo;
    @Autowired
    UserRepository userRepo;

    public Token createToken(String email){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        Token token = new Token();
        token.setToken(tokenValue);
        token.setExpireAt(LocalDateTime.now().plusSeconds(999999999));
        User u=userRepo.findByEmail(email);
        token.setEmail(email);
        tokenRepo.save(token);
        return token;

    }

    public Token updateToken(String email){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        tokenRepo.updateToken(tokenValue,email);
        Token t=tokenRepo.findByEmail(email);
        return t;

    }

    public Token createForgotPassToken(String email){
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII); // this is a sample, you can adapt as per your security need
        tokenRepo.updateForgotPassToken(tokenValue,email);
        return tokenRepo.findByEmail(email);
    }

    public boolean verifyToken(String token,String email){
        Token t=tokenRepo.findByEmail(email);
        try {
            if(t.isExpired()){
                throw new TokenExpiredException("token is expired new token generated");
            }
            if(t.getToken()==null){
                throw new TokenNotFoundException("token should be valid") ;
            }
        }
        catch (EmailExistsException e){
            updateToken(email);
        }

        if(t.getToken().equals(token)){
            return true;
        }
        else
            return false;
    }
    public boolean verifyForgotPassToken(String token,String email){
        Token t=tokenRepo.findByEmail(email);
        try {
            if(t.isExpired()){
                throw new TokenExpiredException("token is expired new token generated");
            }
            if(t.getForgotPassToken()==null){
               throw new TokenNotFoundException("token should be valid") ;
            }
        }
        catch (TokenExpiredException e){
            updateToken(email);
        }

        if(t.getForgotPassToken().equals(token)){
            return true;
        }
        else
            return false;
    }


}
