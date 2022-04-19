package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    void deleteById(Long id);

    User getById(Long id);

    List<User> findAll();
}
