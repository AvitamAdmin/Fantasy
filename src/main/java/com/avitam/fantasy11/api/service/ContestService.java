package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.ContestWsDto;
import com.avitam.fantasy11.model.Contest;


public interface ContestService {

    Contest findByRecordId(String recordId);

    void deleteByRecordId(String recordId);

    ContestWsDto handleEdit(ContestWsDto request);

    void updateByRecordId(String recordId);

//    double getContestWinningAmount (double entryFee, int slotFilled, double profitPercentage);

}
