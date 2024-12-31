package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.GatewaysAutomaticDto;
import com.avitam.fantasy11.api.dto.GatewaysAutomaticWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.GatewaysAutomaticService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.GatewaysAutomatic;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.GatewaysAutomaticRepository;
import java.util.ArrayList;
import java.util.List;
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
    public GatewaysAutomaticWsDto handleEdit(GatewaysAutomaticWsDto gatewaysAutomaticWsDto) {
        GatewaysAutomaticDto gatewaysAutomaticDto = new GatewaysAutomaticDto();
        GatewaysAutomatic gatewaysAutomatic = null;
        List<GatewaysAutomaticDto> requestData = gatewaysAutomaticWsDto.getGatewaysAutomaticDtoList();
        List<GatewaysAutomatic> gatewaysAutomatics = new ArrayList<>();
        for(GatewaysAutomaticDto gatewaysAutomaticDto1:requestData) {
            if (gatewaysAutomaticDto1.getRecordId() != null) {
                gatewaysAutomaticRepository.findByRecordId(gatewaysAutomaticDto1.getRecordId());
                modelMapper.map(requestData, gatewaysAutomatic);
            } else {
                if (baseService.validateIdentifier(EntityConstants.GATEWAYS_AUTOMATIC, gatewaysAutomaticDto1.getGatewaysAutomatic().getIdentifier()) != null) {
                    gatewaysAutomaticWsDto.setSuccess(false);
                    gatewaysAutomaticWsDto.setMessage("Identifier already present");
                    return gatewaysAutomaticWsDto;
                }
                gatewaysAutomatic = modelMapper.map(gatewaysAutomaticDto, GatewaysAutomatic.class);
            }
            if (gatewaysAutomaticDto.getLogo() != null && !gatewaysAutomaticDto.getLogo().isEmpty()) {
                try {
                    gatewaysAutomatic.setLogo(new Binary(gatewaysAutomaticDto.getLogo().getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                    gatewaysAutomaticWsDto.setMessage("Error processing image file");
                    return gatewaysAutomaticWsDto;
                }
            }
            baseService.populateCommonData(gatewaysAutomatic);
            gatewaysAutomaticRepository.save(gatewaysAutomatic);
            if (gatewaysAutomatic.getRecordId() == null) {
                gatewaysAutomatic.setRecordId(String.valueOf(gatewaysAutomatic.getId().getTimestamp()));
            }
            gatewaysAutomaticRepository.save(gatewaysAutomatic);
            gatewaysAutomatics.add(gatewaysAutomatic);
            gatewaysAutomaticWsDto.setMessage("Updated successfully!");
            gatewaysAutomaticWsDto.setBaseUrl(ADMIN_GATEWAYSAUTOMATIC);
        }
        gatewaysAutomaticWsDto.setGatewaysAutomaticDtoList(modelMapper.map(gatewaysAutomatic,List.class));
        return gatewaysAutomaticWsDto;
    }



}
