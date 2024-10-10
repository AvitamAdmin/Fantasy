package com.avitam.fantasy11.core.service.impl;

import com.avitam.fantasy11.model.Role;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        User user = userRepository.findByEmail(inputString);
        User user1=userRepository.findByMobileNumber(inputString);
        if ((user == null || !user.getStatus())&&(user1==null || !user1.getStatus()) ) throw new UsernameNotFoundException(inputString);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        }
            return new org.springframework.security.core.userdetails.User(user1.getMobileNumber(), user1.getPassword(), grantedAuthorities);
    }
}
