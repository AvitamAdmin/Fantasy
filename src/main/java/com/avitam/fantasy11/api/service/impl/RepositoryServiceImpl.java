package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.service.RepositoryService;
import com.avitam.fantasy11.repository.generic.GenericImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public GenericImportRepository getRepositoryForName(String entityName) {
        return (GenericImportRepository) applicationContext.getBean(entityName + "Repository");
    }
}
