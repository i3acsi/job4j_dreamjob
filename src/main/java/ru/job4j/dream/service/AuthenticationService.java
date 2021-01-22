package ru.job4j.dream.service;

import ru.job4j.dream.model.Role;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;

public class AuthenticationService {
    private final static String SALT = "DEFAULT_SALT";
    private final static long LIFE_TIME_MINUTES = 30L;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String encode(String email, String password) {
        return Base64.getEncoder().encodeToString((email + SALT + password).getBytes());
    }

    public static boolean checkCredentials(User user, String enteredPwd) {
        return Arrays.equals((user.getEmail() + SALT + enteredPwd).getBytes(), Base64.getDecoder().decode(user.getPassword()));
    }

    public static String getToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        return Base64.getEncoder().encodeToString((user.getId() + SALT + now.format(FORMATTER)).getBytes());
    }

    public static boolean checkToken(String token, Role role) {
        String[] params = new String(Base64.getDecoder().decode(token)).split(SALT);
        LocalDateTime tokenCreationDateTime = LocalDateTime.parse(params[1], FORMATTER);
        LocalDateTime now = LocalDateTime.now();
        long minutesPassed = ChronoUnit.MINUTES.between(tokenCreationDateTime, now);
        User user = PsqlStore.instOf().findUserById(Integer.parseInt(params[0]));
        if (minutesPassed >= LIFE_TIME_MINUTES)
            return false;
        if (role != null && !user.hasRole(role)) {
            return false;
        }
        return true;
    }

    public static String[] decodeToUserNameAndPwd(String encodedCredentials) {
        return new String(Base64.getDecoder().decode(encodedCredentials)).split(SALT);
    }
}