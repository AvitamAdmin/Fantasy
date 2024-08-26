package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.AddressRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Optional<Address> findByRecordId(String recordId) {
        return addressRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        addressRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Optional<Address>  addressOptional=addressRepository.findByRecordId(recordId);
        if(addressOptional.isPresent())
        {
            addressRepository.save(addressOptional.get());
        }
    }


}
