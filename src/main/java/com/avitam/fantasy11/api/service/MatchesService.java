package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Matches;

import java.util.Optional;

public interface MatchesService {

    public Optional<Matches> findByRecordId(String recordId);

    public  void deleteByRecordId(String recordId);

    public void updateByRecordId(String recordId);
}
