package com.example.demo.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptUtil {

    public String hashPassword(String password) {
        try {
            password = BCrypt.with(BCrypt.Version.VERSION_2B).hashToString(12, password.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return "{bcrypt}"+password;
    }

}
