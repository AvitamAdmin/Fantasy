package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.RepositoryService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.CommonFields;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private CoreService coreService;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public void populateCommonData(CommonFields requestData) {
        String creator=coreService.getCurrentUser().getUsername();
        if (requestData.getCreator() == null) {
            requestData.setCreator(creator);
        }
        if (requestData.getCreationTime() == null) {
            requestData.setCreationTime(new Date());
        }
        requestData.setModifiedBy(creator);
        requestData.setLastModified(new Date());
    }


    @Override
    public CommonFields validateIdentifier(String entityName, String identifier) {
        GenericImportRepository genericImportRepository = repositoryService.getRepositoryForName(entityName);
        return genericImportRepository.findByIdentifier(identifier);
    }
}
