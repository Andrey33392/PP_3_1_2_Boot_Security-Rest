package ru.kata.spring.boot_security.demo.reposirory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface RoleRepository {

    Set<Role> getRolesByIds(List<Long> ids);

    List<Role> findAll();
}
