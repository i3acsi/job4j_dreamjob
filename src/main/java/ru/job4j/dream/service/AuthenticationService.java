package ru.job4j.dream.service;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;

public class AuthenticationService {
    private final static String SALT = "DEFAULT_SALT";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String encode(String userName, String password) {
        return Base64.getEncoder().encodeToString((userName + SALT + password).getBytes());
    }

    public static boolean checkCredentials(String userName, String password, String expected) {
        return Arrays.equals((userName + SALT + password).getBytes(), Base64.getDecoder().decode(expected));
    }

    public static String getToken(String userName, String password) {
        LocalDateTime now = LocalDateTime.now();
        return Base64.getEncoder().encodeToString((userName + SALT + password + SALT + now.format(FORMATTER)).getBytes());
    }

    public static boolean checkToken(String token){
        boolean result = false;
                String[] params = new String(Base64.getDecoder().decode(token)).split(SALT);
                LocalDateTime tokenCreationDateTime = LocalDateTime.parse(params[2], FORMATTER);
                LocalDateTime now = LocalDateTime.now();


        }
    }

    public static String[] decodeToUserNameAndPwd (String encodedCredentials) {
        return new String(Base64.getDecoder().decode(encodedCredentials)).split(SALT);
    }
}