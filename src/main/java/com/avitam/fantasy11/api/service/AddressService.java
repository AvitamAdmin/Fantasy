package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.AddressWsDto;
import com.avitam.fantasy11.model.Address;

public interface AddressService {

    Address findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    AddressWsDto handleEdit(AddressWsDto request);

    void updateByRecordId(String recordId);
}
