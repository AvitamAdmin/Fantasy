package com.avitam.fantasy11.core.service.impl;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CoreServiceImpl implements CoreService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        if (userRepository.findByUsername(principal.getUsername()) != null) {
            return userRepository.findByUsername(principal.getUsername());
        }
        return userRepository.findByMobileNumber(principal.getUsername());
    }
}
