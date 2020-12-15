package com.akromo.dao;

import com.akromo.models.Role;
import com.akromo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = (User) entityManager.createQuery("from User where username = :name")
                .setParameter("name", name).getSingleResult();
        return user;
    }

    @Override
    public void updateUser(User user) {
        User updatingUser = entityManager.find(User.class, user.getId());
        updatingUser.setEmail(user.getEmail());
        updatingUser.setUsername(user.getUsername());
        updatingUser.setPassword(user.getPassword());
        updatingUser.setRoles(user.getRoles());
    }

    @Override
    public void remove(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public List<User> listUsers() {
        Query query = entityManager.createQuery("from User");
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    public List<Role> getAllRoles() {
        Query query = entityManager.createQuery("from Role");
        List<Role> roles = query.getResultList();
        return roles;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = (Role) entityManager.createQuery("from Role where name = :name")
                .setParameter("name", name).getSingleResult();
        return role;
    }
}
