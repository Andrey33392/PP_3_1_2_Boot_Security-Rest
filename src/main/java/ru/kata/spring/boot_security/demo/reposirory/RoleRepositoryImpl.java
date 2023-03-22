package ru.kata.spring.boot_security.demo.reposirory;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getRolesByIds(List<Long> ids) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT roles FROM Role roles WHERE roles.id=:ids", Role.class)
                .setParameter("ids", ids);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r").getResultList();
    }
}
