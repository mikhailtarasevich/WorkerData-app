package com.mikhail.tarasevich.workerdataapp.repository.impl;

import com.mikhail.tarasevich.workerdataapp.repository.UserRepository;
import com.mikhail.tarasevich.workerdataapp.model.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl extends AbstractCrudRepositoryImpl<User> implements UserRepository {

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class, "email");
    }

}

