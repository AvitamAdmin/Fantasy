package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.UserWinningsService;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.model.UserWinningsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserWinningsServiceImpl implements UserWinningsService {
    @Autowired
    private UserWinningsRepository userWinningsRepository;
    @Override
    public Optional<UserWinnings> findByRecordId(String recordId) {
        return userWinningsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        userWinningsRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<UserWinnings> userWinningsOptional=userWinningsRepository.findByRecordId(recordId);
        userWinningsOptional.ifPresent(userWinnings -> userWinningsRepository.save(userWinnings));
    }
}
