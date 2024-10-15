package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.repository.AddressRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private AddressRepository addressDao;

    public static final String ADMIN_ADDRESS = "/admin/address";

    @Override
    public Address findByRecordId(String recordId) {
        return addressDao.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        addressDao.deleteByRecordId(recordId);
    }

    @Override
    public AddressDto handleEdit(AddressDto request) {
        AddressDto addressDto = new AddressDto();
        Address address = null;
        if(request.getRecordId()!=null){
            Address requestData = request.getAddress();
            address = addressDao.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,address);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.ADDRESS,request.getAddress().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            address=request.getAddress();
        }
        baseService.populateCommonData(address);
        addressDao.save(address);
        if(request.getRecordId()==null){
            address.setRecordId(String.valueOf(address.getId().getTimestamp()));
        }
        addressDao.save(address);
        addressDto.setAddress(address);
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Address  addressOptional=addressDao.findByRecordId(recordId);
        if(addressOptional!=null)
        {
            addressDao.save(addressOptional);
        }
    }


}
