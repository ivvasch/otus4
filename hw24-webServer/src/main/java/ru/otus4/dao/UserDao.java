package ru.otus4.dao;

import ru.otus4.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);
}