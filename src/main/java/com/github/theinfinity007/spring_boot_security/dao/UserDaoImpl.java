package com.github.theinfinity007.spring_boot_security.dao;

import com.github.theinfinity007.spring_boot_security.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDao {

    EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE username =:username and enabled=true", User.class);
        query.setParameter("username", username);

        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception ex) {
            // silent
        }

        return user;
    }


    @Override
    @Transactional
    public void save(User user) {
        entityManager.merge(user);
    }
}
