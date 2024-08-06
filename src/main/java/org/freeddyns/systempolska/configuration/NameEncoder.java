package org.freeddyns.systempolska.configuration;

import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Configuration
public class NameEncoder {

    public String encode(String password) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");


            byte[] passwordBytes = password.getBytes();


            byte[] hashedBytes = md.digest(passwordBytes);


            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }
}
