package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.reposirory.RoleRepository;
import ru.kata.spring.boot_security.demo.reposirory.UserRepository;
import ru.kata.spring.boot_security.demo.util.UserNotFoundException;

import javax.transaction.Transactional;
import java.rmi.AlreadyBoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;




    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;


    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.getById(id));
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    @Override
    public void saveUser(User user) {

        userRepository.saveUser(user);

    }



    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);

    }

    @Transactional
    @Override
    public void updateUser(User user) {


        userRepository.updateUser(user);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }




}
