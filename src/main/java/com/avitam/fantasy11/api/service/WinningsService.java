package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.WinningsWsDto;
import com.avitam.fantasy11.model.Winnings;

public interface WinningsService {

    Winnings findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);

    WinningsWsDto handleEdit(WinningsWsDto request);
}
