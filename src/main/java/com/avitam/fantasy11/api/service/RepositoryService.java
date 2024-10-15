package com.avitam.fantasy11.api.service;

import com.avitam.fantasy11.repository.generic.GenericImportRepository;

public interface RepositoryService {

    GenericImportRepository getRepositoryForName(String entityName);
}
