package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.GatewaysManualDto;
import com.avitam.fantasy11.api.dto.GatewaysManualWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.GatewaysManualService;
import com.avitam.fantasy11.model.GatewaysManual;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.GatewaysManualRepository;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GatewaysManualServiceImpl implements GatewaysManualService {

    @Autowired
    private GatewaysManualRepository gatewaysManualRepository;
    @Autowired
    private ModelMapper modelMapper;
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
    public GatewaysManualWsDto handleEdit(GatewaysManualWsDto gatewaysManualWsDto) {
        GatewaysManualDto gatewaysManualDto = new GatewaysManualDto();
        GatewaysManual gatewaysManual = null;
        List<GatewaysManual> gatewaysManuals = new ArrayList<>();
        List<GatewaysManualDto> gatewaysManualDtoList = gatewaysManualWsDto.getGatewaysManualDtoList();
        for (GatewaysManualDto gatewaysManualDto1 : gatewaysManualDtoList) {
            if (gatewaysManualDto1.getRecordId() != null) {
                gatewaysManual = gatewaysManualRepository.findByRecordId(gatewaysManualDto1.getRecordId());
                modelMapper.map(gatewaysManualDto1, gatewaysManual);
                gatewaysManualRepository.save(gatewaysManual);
            } else {
                if (baseService.validateIdentifier(EntityConstants.GATEWAYS_MANUAL, gatewaysManualDto1.getIdentifier()) != null) {
                    gatewaysManualWsDto.setSuccess(false);
                    gatewaysManualWsDto.setMessage("Identifier already present");
                    return gatewaysManualWsDto;
                }
                gatewaysManual = modelMapper.map(gatewaysManualDto, GatewaysManual.class);
            }
            if (gatewaysManualDto.getLogo() != null && !gatewaysManualDto.getLogo().isEmpty()) {
                try {
                    gatewaysManual.setLogo(new Binary(gatewaysManualDto.getLogo().getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                    gatewaysManualWsDto.setMessage("Error processing image file");
                    return gatewaysManualWsDto;
                }
            }
            baseService.populateCommonData(gatewaysManual);
            gatewaysManualRepository.save(gatewaysManual);
            if (gatewaysManual.getRecordId() == null) {
                gatewaysManual.setRecordId(String.valueOf(gatewaysManual.getId().getTimestamp()));
            }
            gatewaysManualRepository.save(gatewaysManual);
            gatewaysManuals.add(gatewaysManual);
            gatewaysManualWsDto.setMessage("Manuals updated successfully!");
            gatewaysManualWsDto.setBaseUrl(ADMIN_GATEWAYSMANUAL);
        }
        gatewaysManualWsDto.setGatewaysManualDtoList(modelMapper.map(gatewaysManual, List.class));
        return gatewaysManualWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        GatewaysManual gatewaysManualOptional = gatewaysManualRepository.findByRecordId(recordId);
        if (gatewaysManualOptional != null) {
            gatewaysManualRepository.save(gatewaysManualOptional);
        }
    }

}
