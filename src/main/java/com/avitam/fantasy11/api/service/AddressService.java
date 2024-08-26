package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.model.Address;

import java.util.Optional;

public interface AddressService {

    Optional<Address> findByRecordId(String recordId);

    void deleteByRecordId(String recordId) ;



    void updateByRecordId(String recordId);
}
