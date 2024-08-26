package com.avitam.fantasy11.api.service;


import com.avitam.fantasy11.model.LineUpStatus;

import java.util.Optional;

public interface LineUpStatusService {


    Optional<LineUpStatus> findByRecordId(String recordId);



    void deleteByRecordId(String recordId);

    void updateByRecordId(String recordId);
}
