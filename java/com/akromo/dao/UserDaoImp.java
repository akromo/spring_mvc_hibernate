package com.akromo.dao;

import com.akromo.models.Role;
import com.akromo.models.User;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImp implements UserDao {

    private final EntityManager entityManager;

    private void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    private void updateRolesForUser(User user) {
        Role managedRole = null;
        Set<Role> managedRoles = new HashSet<>();
        for (Role rawRole : user.getRoles()) {
            try {
                managedRole = (Role) entityManager.createQuery("from Role where name = :name")
                        .setParameter("name", rawRole.getName()).getSingleResult();
            } catch (NoResultException e) {
                managedRole = rawRole;
                entityManager.persist(managedRole);
            } finally {
                managedRoles.add(managedRole);
            }
        }
        user.setRoles(managedRoles);
    }

    public UserDaoImp(LocalContainerEntityManagerFactoryBean emf) {
        this.entityManager = emf.getObject().createEntityManager();

    }

    @Override
    public void add(User user) {
        beginTransaction();
        updateRolesForUser(user);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User getUser(Long id) {
        beginTransaction();
        User user = entityManager.find(User.class, id);
        entityManager.getTransaction().commit();
        return user;

    }

    @Override
    public User getUserByName(String name) {
        beginTransaction();
        User user = (User) entityManager.createQuery("from User where username = :name")
                .setParameter("name", name).getSingleResult();
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public void updateUser(User user) {
        beginTransaction();
        User updatingUser = entityManager.find(User.class, user.getId());
        updatingUser.setEmail(user.getEmail());
        updatingUser.setUsername(user.getUsername());
        updatingUser.setPassword(user.getPassword());
        updatingUser.setRoles(user.getRoles());
        updateRolesForUser(updatingUser);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(long id) {
        beginTransaction();
        entityManager.remove(entityManager.find(User.class, id));
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> listUsers() {
        beginTransaction();
        Query query = entityManager.createQuery("from User");
        List<User> users = query.getResultList();
        entityManager.getTransaction().commit();
        return users;
    }
}
