package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.MatchesDto;
import com.avitam.fantasy11.model.Matches;

import java.util.Optional;

public interface MatchesService {

    public Matches findByRecordId(String recordId);

    public  void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);

    MatchesDto handleEdit(MatchesDto request, int flag);
}
