package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.AddressRepository;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.model.GatewaysAutomaticRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GatewaysAutomaticServiceImpl implements GatewaysAutomaticService {

    @Autowired
    private GatewaysAutomaticRepository gatewaysAutomaticRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    public static final String ADMIN_GATEWAYSAUTOMATIC = "/admin/gatewaysAutomatic";

    @Override
    public GatewaysAutomatic findByRecordId(String recordId) {
        return gatewaysAutomaticRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        gatewaysAutomaticRepository.deleteByRecordId(recordId);
    }

    @Override
    public GatewaysAutomaticDto handleEdit(GatewaysAutomaticDto request) {
        GatewaysAutomaticDto gatewaysAutomaticDto = new GatewaysAutomaticDto();
        GatewaysAutomatic gatewaysAutomatic = null;
        if(request.getRecordId()!=null){
            GatewaysAutomatic requestData = request.getGatewaysAutomatic();
            gatewaysAutomatic = gatewaysAutomaticRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, gatewaysAutomatic);
        }
        else {
            gatewaysAutomatic=request.getGatewaysAutomatic();
            gatewaysAutomatic.setCreator(coreService.getCurrentUser().getUsername());
            gatewaysAutomatic.setCreationTime(new Date());
            gatewaysAutomaticRepository.save(gatewaysAutomatic);
        }
        gatewaysAutomatic.setLastModified(new Date());
        if(request.getRecordId()==null){
            gatewaysAutomatic.setRecordId(String.valueOf(gatewaysAutomatic.getId().getTimestamp()));
        }
        gatewaysAutomaticRepository.save(gatewaysAutomatic);
        gatewaysAutomaticDto.setGatewaysAutomatic(gatewaysAutomatic);
        gatewaysAutomaticDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        return gatewaysAutomaticDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        GatewaysAutomatic  gatewaysAutomaticOptional=gatewaysAutomaticRepository.findByRecordId(recordId);
        if(gatewaysAutomaticOptional!=null)
        {
            gatewaysAutomaticRepository.save(gatewaysAutomaticOptional);
        }
    }

}
