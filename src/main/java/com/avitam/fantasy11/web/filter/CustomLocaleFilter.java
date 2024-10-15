package com.avitam.fantasy11.web.filter;

import com.avitam.fantasy11.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class CustomLocaleFilter extends GenericFilterBean {

    @Autowired
    UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principalObject = (User) authentication.getPrincipal();
        //avitam.fantasy11.core.model.User localeUser =userRepository.findByUsername(principalObject.getUsername());
        System.out.println(principalObject.getUsername());
        System.out.println(userRepository);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
