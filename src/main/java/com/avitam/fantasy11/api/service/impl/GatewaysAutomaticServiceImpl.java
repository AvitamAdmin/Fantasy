package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.GatewaysAutomaticRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GatewaysAutomaticServiceImpl implements GatewaysAutomaticService {

    @Autowired
    private GatewaysAutomaticRepository gatewaysAutomaticRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

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
            if(baseService.validateIdentifier(EntityConstants.GATEWAYS_AUTOMATIC,request.getGatewaysAutomatic().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            gatewaysAutomatic=request.getGatewaysAutomatic();
        }
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            try {
                gatewaysAutomatic.setLogo(new Binary(request.getLogo().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                gatewaysAutomaticDto.setMessage("Error processing image file");
                return gatewaysAutomaticDto;
            }
        }
        baseService.populateCommonData(gatewaysAutomatic);
        gatewaysAutomaticRepository.save(gatewaysAutomatic);
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
