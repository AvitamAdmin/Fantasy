package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.MatchType;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MatchTypeService {


    public Optional<MatchType> findByRecordId(String recordId);



    public void deleteByRecordId(String recordId);


    public void updateByRecordId(String recordId);
}
