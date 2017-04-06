package com.theironyard.utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class KeyUtil {

    static Key key = MacProvider.generateKey();

    public static String getJwt(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static boolean validateJwt(String jwt, String username) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject().equals(username);
    }

}
