package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.AddressRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    public static final String ADMIN_ADDRESS = "/admin/address";

    @Override
    public Address findByRecordId(String recordId) {
        return addressRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        addressRepository.deleteByRecordId(recordId);
    }

    @Override
    public AddressDto handleEdit(AddressDto request) {
        AddressDto addressDto = new AddressDto();
        Address address = null;
        if(request.getRecordId()!=null){
            Address requestData = request.getAddress();
            address = addressRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,address);
            addressRepository.save(address);
        }
        else {
            address=request.getAddress();
            address.setCreator(coreService.getCurrentUser().getUsername());
            address.setCreationTime(new Date());
            addressRepository.save(address);
        }
        address.setLastModified(new Date());
        if(request.getRecordId()==null){
            address.setRecordId(String.valueOf(address.getId().getTimestamp()));
        }
        addressRepository.save(address);
        addressDto.setAddress(address);
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Address  addressOptional=addressRepository.findByRecordId(recordId);
        if(addressOptional!=null)
        {
            addressRepository.save(addressOptional);
        }
    }


}
