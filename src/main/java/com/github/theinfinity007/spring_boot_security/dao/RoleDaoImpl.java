package com.github.theinfinity007.spring_boot_security.dao;

import com.github.theinfinity007.spring_boot_security.entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao{

    EntityManager entityManager;

    @Autowired
    RoleDaoImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Role findByRoleName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role WHERE name=:name", Role.class);
        query.setParameter("name", name);

        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (Exception ex){
            // silent
        }

        return role;
    }
}
