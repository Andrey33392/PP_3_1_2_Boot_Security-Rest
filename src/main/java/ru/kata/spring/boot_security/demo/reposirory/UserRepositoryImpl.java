package ru.kata.spring.boot_security.demo.reposirory;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public String passwordCode(String pass) {

        return passwordEncoder.encode(pass);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u from User u", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
        String pass = passwordCode(user.getPassword());
        user.setPassword(pass);
        entityManager.persist(user);
    }



    @Override
    public void deleteUserById(Long id) {
        Query query = entityManager.createQuery("delete from User user where user.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }

    @Override
    public void updateUser(User user) {
        String pass = passwordCode(user.getPassword());
        user.setPassword(pass);
        entityManager.merge(user);

    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = (entityManager.createQuery("SELECT user FROM  User user Join fetch  user.roles WHERE  user.email=:email", User.class));
        query.setParameter("email", email);
        return query.getSingleResult();



    }
}
