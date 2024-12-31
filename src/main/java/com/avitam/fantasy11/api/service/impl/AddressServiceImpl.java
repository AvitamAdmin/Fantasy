package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.AddressWsDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.repository.AddressRepository;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private AddressRepository addressRepository;

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
    public AddressWsDto handleEdit(AddressWsDto addressWsDto) {
        Address address = null;
        List<Address> addresses = new ArrayList<>();
        List<AddressDto> addressDtoList = addressWsDto.getAddressDtoList();
        for (AddressDto addressDto1 : addressDtoList) {
            if (addressDto1.getRecordId() != null) {
                address = addressRepository.findByRecordId(addressDto1.getRecordId());
                modelMapper.map(addressDto1, address);
                addressRepository.save(address);
                addressWsDto.setMessage("Address updated successfully!");
            } else {
//                if (baseService.validateIdentifier(EntityConstants.ADDRESS, addressDto1.getIdentifier()) != null) {
//                    addressWsDto.setSuccess(false);
//                    addressWsDto.setMessage("Identifier already present");
//                    return addressWsDto;
//                }
                address = modelMapper.map(addressDto1, Address.class);
            }
            baseService.populateCommonData(address);
            address.setStatus(true);
            addressRepository.save(address);
            if (address.getRecordId() == null) {
                address.setRecordId(String.valueOf(address.getId().getTimestamp()));
            }
            addressRepository.save(address);
            addressWsDto.setMessage("Address added successfully!");
            addresses.add(address);

            addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        }
        addressWsDto.setAddressDtoList(modelMapper.map(addresses, List.class));
        return addressWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Address address = addressRepository.findByRecordId(recordId);
        if (address != null) {
            addressRepository.save(address);
        }
    }


}
