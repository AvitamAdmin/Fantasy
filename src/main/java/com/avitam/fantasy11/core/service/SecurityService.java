package com.avitam.fantasy11.core.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}

