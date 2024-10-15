package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.model.UserWinnings;

public interface UserWinningsService {

    UserWinnings findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    UserWinningsDto handleEdit(UserWinningsDto request);
}
