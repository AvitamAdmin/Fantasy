package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ExtensionDto;
import com.avitam.fantasy11.api.service.ExtensionService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Extension;
import com.avitam.fantasy11.model.ExtensionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExtensionServiceImpl implements ExtensionService {

    @Autowired
    private ExtensionRepository extensionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
            extension = request.getExtension();
            extension.setCreator(coreService.getCurrentUser().getUsername());
            extension.setCreationTime(new Date());
            extensionRepository.save(extension);
        }
        extension.setLastModified(new Date());
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




