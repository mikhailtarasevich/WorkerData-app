package com.mikhail.tarasevich.workerdataapp.repository.impl;

import com.mikhail.tarasevich.workerdataapp.repository.RoleRepository;
import com.mikhail.tarasevich.workerdataapp.model.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends AbstractCrudRepositoryImpl<Role> implements RoleRepository {

    @Autowired
    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Role.class, "name");
    }

}
