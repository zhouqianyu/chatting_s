package com.chatting.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class Token {
    private Map<String, Object> claims = null;
    private String secret = "<<<<<<jh?fso;eg?nkhjgn;iua?sihet8'3iw38/3904<<<<<";
    private Algorithm algorithm;
    Logger logger = Logger.getLogger(Token.class);
    @Resource
    private Cache cache;
    @Resource
    RandomString randomString;
    public Token put(String key, Object value) {
        if (claims == null) claims = new HashMap<String, Object>();
        claims.put(key, value);
        return this;
    }

    public String build() {
        ObjectMapper mapper = new ObjectMapper();
        String token = null;
        try {
            algorithm = Algorithm.HMAC512(secret);
            String session = randomString.getRandomString(16);
            claims.put("session", session);
            String json = mapper.writeValueAsString(claims);
            token = JWT.create().withClaim("payload", json).sign(algorithm);
            cache.set(TOKEN_PREFIX + claims.get("uuid"), token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    public Map<String, Object> verify(String token) {
        DecodedJWT jwt;
        ObjectMapper mapper;
        Map<String, Object> map;
        try {
            algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
            String payload = jwt.getClaim("payload").asString();
            mapper = new ObjectMapper();
            map = mapper.readValue(payload, Map.class);
            if (map != null) {
                Object userToken = cache.get(TOKEN_PREFIX + map.get("uuid"));
                if (userToken!=null&&userToken.equals(token)) return map;
                return map;
            } else return null;
        } catch (JWTVerificationException e) {
            logger.error(e.getMessage());
            return null;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean trashToken(String token) {
        try {
            Map<String, Object> user = verify(token);
            if (user != null) {
                cache.remove(TOKEN_PREFIX + user.get("uuid").toString());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private final String TOKEN_PREFIX = "user_token_";
}
