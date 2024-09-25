package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.api.service.GatewaysManualService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.model.GatewaysAutomaticRepository;
import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.model.GatewaysManualRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GatewaysManualServiceImpl implements GatewaysManualService {

    @Autowired
    private GatewaysManualRepository gatewaysManualRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
            gatewaysManual=request.getGatewaysManual();
            gatewaysManual.setCreator(coreService.getCurrentUser().getUsername());
            gatewaysManual.setCreationTime(new Date());
            gatewaysManualRepository.save(gatewaysManual);
        }
        gatewaysManual.setLastModified(new Date());
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
