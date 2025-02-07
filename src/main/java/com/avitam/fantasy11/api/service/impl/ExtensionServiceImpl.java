package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.dto.ExtensionWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ExtensionService;
import com.avitam.fantasy11.model.Extension;
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
    private BaseService baseService;

    private static final String ADMIN_EXTENSION = "/admin/extension";

    @Override
    public Extension findByRecordId(String recordId) {
        return extensionRepository.findByRecordId(recordId);
    }


    @Override
    public ExtensionWsDto handleEdit(ExtensionWsDto request) {

        Extension extension = null;
        List<ExtensionDto> extensionDtos = request.getExtensionDtoList();
        List<Extension> extensionList = new ArrayList<>();

        for (ExtensionDto extensionDto1 : extensionDtos) {
            if (extensionDto1.getRecordId() != null) {
                extension = extensionRepository.findByRecordId(extensionDto1.getRecordId());
                modelMapper.map(extensionDto1, extension);
                extension.setLastModified(new Date());
                extensionRepository.save(extension);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.EXTENSION, extensionDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                extension = modelMapper.map(extensionDto1, Extension.class);

                if (extensionDto1.getImages() != null) {
                    try {
                        extension.setImage(new Binary(extensionDto1.getImages().getBytes()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                baseService.populateCommonData(extension);
                extension.setStatus(true);
                extensionRepository.save(extension);
                if (extension.getRecordId() == null) {
                    extension.setRecordId(String.valueOf(extension.getId().getTimestamp()));
                }
                extensionRepository.save(extension);
                request.setMessage("Data added Successfully");
            }
            extensionList.add(extension);
        }
        request.setBaseUrl(ADMIN_EXTENSION);
        request.setExtensionDtoList(modelMapper.map(extensionList, List.class));
        return request;
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