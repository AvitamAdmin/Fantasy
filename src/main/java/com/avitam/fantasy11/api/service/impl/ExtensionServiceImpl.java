package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.dto.ExtensionWsDto;
import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.dto.KYCWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ExtensionService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.ExtensionRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    @Autowired
    private ExtensionRepository extensionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_EXTENSION = "/admin/extension";

    @Override
    public Extension findByRecordId(String recordId) {
        return extensionRepository.findByRecordId(recordId);
    }


    @Override
    public ExtensionWsDto handleEdit(ExtensionWsDto request) {
        ExtensionWsDto extensionWsDto = new ExtensionWsDto();
        Extension extensionData = null;
        List<ExtensionDto> extensionDtos = request.getExtensionList();
        List<Extension> extensionList = new ArrayList<>();
        ExtensionDto extensionDto = new ExtensionDto();
        for (ExtensionDto extensionDto1 : extensionDtos) {
            if (extensionDto1.getRecordId() != null) {
                extensionData = extensionRepository.findByRecordId(extensionDto1.getRecordId());
                modelMapper.map(extensionDto1, extensionData);
                extensionRepository.save(extensionData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.KYC, extensionDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    //request.setMessage("Identifier already present");
                    return request;
                }

                extensionData = modelMapper.map(extensionDto, Extension.class);
            }

            extensionRepository.save(extensionData);
            extensionData.setLastModified(new Date());
            if (extensionData.getRecordId() == null) {
                extensionData.setRecordId(String.valueOf(extensionData.getId().getTimestamp()));
            }
            extensionRepository.save(extensionData);
            extensionList.add(extensionData);
            extensionWsDto.setMessage("Extension was updated successfully");
            extensionWsDto.setBaseUrl(ADMIN_EXTENSION);
        }
        extensionWsDto.setExtensionList(modelMapper.map(extensionList, List.class));
        return extensionWsDto;
    }


    @Override
    public void deleteByRecordId(String recordId) {
        extensionRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        Extension extension = extensionRepository.findByRecordId(recordId);
        if (extension != null) {

            extensionRepository.save(extension);
        }
    }
}




