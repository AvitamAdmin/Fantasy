package com.avitam.fantasy11.core.service.impl;


import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String inputString) {
        User user = userRepository.findByUsername(inputString);
        User user1 = userRepository.findByMobileNumber(inputString);
        User user2 = userRepository.findByEmail(inputString);


        if ((user == null) && (user1 == null || !user1.getStatus()) && (user2 == null)) {
            throw new UsernameNotFoundException(inputString);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }

        if (user1 != null) {
            return new CustomUserDetails(
                    user1.getMobileNumber(),
                    user1.getStatus(),
                    grantedAuthorities
            );
        }
        return new CustomUserDetails(user2.getEmail(), user2.getStatus(), grantedAuthorities);


    }

}
