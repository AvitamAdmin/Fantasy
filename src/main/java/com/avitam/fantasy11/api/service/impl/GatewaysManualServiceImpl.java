package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.api.service.GatewaysManualService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.GatewaysAutomaticRepository;
import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.repository.GatewaysManualRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GatewaysManualServiceImpl implements GatewaysManualService {

    @Autowired
    private GatewaysManualRepository gatewaysManualRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_GATEWAYSMANUAL = "/admin/gatewaysManual";

    @Override
    public GatewaysManual findByRecordId(String recordId) {
        return gatewaysManualRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        gatewaysManualRepository.deleteByRecordId(recordId);
    }

    @Override
    public GatewaysManualDto handleEdit(GatewaysManualDto request) {
        GatewaysManualDto gatewaysManualDto = new GatewaysManualDto();
        GatewaysManual gatewaysManual = null;
        if(request.getRecordId()!=null){
            GatewaysManual requestData = request.getGatewaysManual();
            gatewaysManual = gatewaysManualRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, gatewaysManual);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.GATEWAYS_MANUAL,request.getGatewaysManual().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            gatewaysManual=request.getGatewaysManual();
        }
        if(request.getLogo()!=null && !request.getLogo().isEmpty()) {
            try {
                gatewaysManual.setLogo(new Binary(request.getLogo().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                gatewaysManualDto.setMessage("Error processing image file");
                return gatewaysManualDto;
            }
        }
        baseService.populateCommonData(gatewaysManual);
        gatewaysManualRepository.save(gatewaysManual);
        if(request.getRecordId()==null){
            gatewaysManual.setRecordId(String.valueOf(gatewaysManual.getId().getTimestamp()));
        }
        gatewaysManualRepository.save(gatewaysManual);
        gatewaysManualDto.setGatewaysManual(gatewaysManual);
        gatewaysManualDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        return gatewaysManualDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        GatewaysManual gatewaysManualOptional=gatewaysManualRepository.findByRecordId(recordId);
        if(gatewaysManualOptional!=null)
        {
            gatewaysManualRepository.save(gatewaysManualOptional);
        }
    }

}
