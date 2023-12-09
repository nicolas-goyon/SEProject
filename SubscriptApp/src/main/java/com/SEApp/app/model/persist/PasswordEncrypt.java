package com.SEApp.app.model.persist;

import io.github.cdimascio.dotenv.Dotenv;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncrypt {

    public final static String SALT = Dotenv.load().get("BCRYPT_SALT");

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, SALT);
    }

    public static boolean checkPassword(String rawpassword, String encryptedPassword) {
        return encrypt(rawpassword).equals(encryptedPassword);
    }
}
