package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ExtensionService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.ExtensionRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    public ExtensionDto handleEdit(ExtensionDto request) {
        ExtensionDto extensionDto = new ExtensionDto();
        Extension extension = null;
        if (request.getRecordId() != null) {
            Extension requestData = request.getExtension();
            extension = extensionRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, extension);
        } else {
            if(baseService.validateIdentifier(EntityConstants.EXTENSION,request.getExtension().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            extension = request.getExtension();
        }
        if(request.getImage()!=null && !request.getImage().isEmpty()) {
            try {
                extension.setImage(new Binary(request.getImage().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                extensionDto.setMessage("Error processing image file");
                return extensionDto;
            }
        }
        baseService.populateCommonData(extension);
        extensionRepository.save(extension);
        if (request.getRecordId() == null) {
            extension.setRecordId(String.valueOf(extension.getId().getTimestamp()));
        }
        extensionRepository.save(extension);
        extensionDto.setExtension(extension);
        extensionDto.setBaseUrl(ADMIN_EXTENSION);
        return extensionDto;
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




