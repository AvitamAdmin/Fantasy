package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.SportTypeDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.SportTypeService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.SportTypeRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SportTypeServiceImpl implements SportTypeService {
    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_SPORTTYPE = "/admin/sportType";

    @Override
    public SportType findByRecordId(String recordId) {

        return sportTypeRepository.findByRecordId(recordId);
    }

    @Override
    public SportTypeDto handleEdit(SportTypeDto request) {
        SportTypeDto sportTypeDto = new SportTypeDto();
        SportType sportType = null;
        if (request.getRecordId() != null) {
            SportType requestData = request.getSportType();
            sportType = sportTypeRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, sportType);
        } else {
            if (baseService.validateIdentifier(EntityConstants.SPORT_TYPE, request.getSportType().getIdentifier()) != null) {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            sportType = request.getSportType();

        }
        if (request.getLogo() != null && !request.getLogo().isEmpty()) {
            try {
                sportType.setLogo(new Binary(request.getLogo().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                sportTypeDto.setMessage("Error processing image file");
                return sportTypeDto;
            }
        }
        baseService.populateCommonData(sportType);
        sportTypeRepository.save(sportType);
        if (request.getRecordId() == null) {
            sportType.setRecordId(String.valueOf(sportType.getId().getTimestamp()));
        }
        sportTypeRepository.save(sportType);
        sportTypeDto.setSportType(sportType);
        sportTypeDto.setBaseUrl(ADMIN_SPORTTYPE);
        return sportTypeDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        sportTypeRepository.findByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        SportType sportType = sportTypeRepository.findByRecordId(recordId);
        if (sportType != null) {

            sportTypeRepository.save(sportType);
        }
    }
}
