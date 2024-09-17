package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.model.Address;

public interface AddressService {

    Address findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;

    AddressDto handleEdit(AddressDto request);

    void updateByRecordId(String recordId);
}
