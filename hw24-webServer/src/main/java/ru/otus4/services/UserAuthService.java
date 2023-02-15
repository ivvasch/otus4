package ru.otus4.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
