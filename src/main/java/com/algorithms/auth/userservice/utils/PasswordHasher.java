package com.algorithms.auth.userservice.utils;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class PasswordHasher {
    private final int IterationCount = 15;

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(IterationCount));
    }


    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
