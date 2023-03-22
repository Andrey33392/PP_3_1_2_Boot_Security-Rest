package ru.kata.spring.boot_security.demo.reposirory;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User getById(Long id);

    void saveUser(User user);


    void deleteUserById(Long id);

    void updateUser(User user);

    User findByEmail(String email);
}
