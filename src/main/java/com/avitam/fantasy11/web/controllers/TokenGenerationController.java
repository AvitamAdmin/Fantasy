package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.tokenGeneration.JWTUtility;
import com.avitam.fantasy11.tokenGeneration.JwtRequest;
import com.avitam.fantasy11.tokenGeneration.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenGenerationController extends BaseController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PostMapping("/api/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) {

        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtUtility.generateToken(userDetails);
        return new JwtResponse(token);

    }


}
