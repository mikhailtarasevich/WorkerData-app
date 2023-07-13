package com.mikhail.tarasevich.workerdataapp.service.impl;

import com.mikhail.tarasevich.workerdataapp.model.entity.User;
import com.mikhail.tarasevich.workerdataapp.repository.UserRepository;
import com.mikhail.tarasevich.workerdataapp.security.UserSecurityDetails;
import com.mikhail.tarasevich.workerdataapp.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByName(email).orElseThrow(() -> new UserNotFoundException("There is no user with email = " + email + " in DB"));

        return new UserSecurityDetails(user, List.of(user.getRole().getName()));
    }

}
