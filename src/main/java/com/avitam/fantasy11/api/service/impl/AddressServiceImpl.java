//package com.avitam.fantasy11.api.service.impl;
//
//import com.avitam.fantasy11.api.dto.AddressDto;
//import com.avitam.fantasy11.api.service.AddressService;
//import com.avitam.fantasy11.api.service.BaseService;
//import com.avitam.fantasy11.core.service.CoreService;
//import com.avitam.fantasy11.model.Address;
//import com.avitam.fantasy11.repository.AddressRepository;
//import com.avitam.fantasy11.repository.EntityConstants;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AddressServiceImpl implements AddressService {
//
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private CoreService coreService;
//    @Autowired
//    private BaseService baseService;
//    @Autowired
//    private AddressRepository addressDao;
//
//    public static final String ADMIN_ADDRESS = "/admin/address";
//
//    @Override
//    public Address findByRecordId(String recordId) {
//        return addressDao.findByRecordId(recordId);
//    }
//
//    @Override
//    public void deleteByRecordId(String recordId) {
//        addressDao.deleteByRecordId(recordId);
//    }
//
//    @Override
//    public AddressDto handleEdit(AddressDto request) {
//        AddressDto addressDto = new AddressDto();
//        Address address = null;
//        if(request.getRecordId()!=null){
//            Address requestData = request.getAddress();
//            address = addressDao.findByRecordId(request.getRecordId());
//            modelMapper.map(requestData,address);
//        }
//        else {
//            if(baseService.validateIdentifier(EntityConstants.ADDRESS,request.getAddress().getIdentifier())!=null)
//            {
//                request.setSuccess(false);
//                request.setMessage("Identifier already present");
//                return request;
//            }
//            address=request.getAddress();
//        }
//        baseService.populateCommonData(address);
//        addressDao.save(address);
//        if(request.getRecordId()==null){
//            address.setRecordId(String.valueOf(address.getId().getTimestamp()));
//        }
//        addressDao.save(address);
//        addressDto.setAddress(address);
//        addressDto.setBaseUrl(ADMIN_ADDRESS);
//        return addressDto;
//    }
//
//    @Override
//    public void updateByRecordId(String recordId) {
//        Address  addressOptional=addressDao.findByRecordId(recordId);
//        if(addressOptional!=null)
//        {
//            addressDao.save(addressOptional);
//        }
//    }
//
//
//}

package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.AddressWsDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.repository.AddressRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public AddressWsDto handleEdit(AddressWsDto addressWsDto) {
        AddressDto addressDto = new AddressDto();
        Address address = null;
        List<Address> addresses = new ArrayList<>();
        List<AddressDto> addressDtoList = addressWsDto.getAddressDtoList();
        for(AddressDto addressDto1 :addressDtoList) {
            if (addressDto1.getRecordId() != null) {
                address = addressDao.findByRecordId(addressDto1.getRecordId());
                modelMapper.map(addressDto1, address);
            } else {
                if (baseService.validateIdentifier(EntityConstants.ADDRESS, addressDto1.getIdentifier()) != null) {
                    addressWsDto.setSuccess(false);
                    addressWsDto.setMessage("Identifier already present");
                    return addressWsDto;
                }
                address = modelMapper.map(addressDto, Address.class);
            }
//            baseService.populateCommonData(address);
//            address.setCreator(coreService.getCurrentUser().getCreator());
            addressDao.save(address);
            address.setModifiedBy(String.valueOf(new Date()));
            if (addressWsDto.getRecordId() == null) {
                address.setRecordId(String.valueOf(address.getId().getTimestamp()));
            }
            addressDao.save(address);
            addresses.add(address);
            addressWsDto.setMessage("Address updated successfully!");
            addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        }
        addressWsDto.setAddressDtoList(modelMapper.map(address,List.class));
        return addressWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        Address address=addressDao.findByRecordId(recordId);
        if(address!=null)
        {
            addressDao.save(address);
        }
    }


}
