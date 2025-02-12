package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.SportTypeDto;
import com.avitam.fantasy11.api.dto.SportTypeWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.SportTypeService;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.SportTypeRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SportTypeServiceImpl implements SportTypeService {
    private static final String ADMIN_SPORTTYPE = "/admin/sportType";
    @Autowired
    private SportTypeRepository sportTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public SportType findByRecordId(String recordId) {

        return sportTypeRepository.findByRecordId(recordId);
    }

    @Override
    public SportTypeWsDto handleEdit(SportTypeWsDto request) {
        List<SportTypeDto> sportTypeDtos = request.getSportTypeDtoList();
        List<SportType> sportTypeList = new ArrayList<>();
        SportType sportType = null;

        for (SportTypeDto sportTypeDto : sportTypeDtos) {
            if (sportTypeDto.getRecordId() != null) {
                sportType = sportTypeRepository.findByRecordId(sportTypeDto.getRecordId());
                modelMapper.map(sportTypeDto, sportType);
                sportType.setLastModified(new Date());
                sportTypeRepository.save(sportType);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.SPORT_TYPE, sportTypeDto.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                sportType = modelMapper.map(sportTypeDto, SportType.class);

                if (sportTypeDto.getLogo() != null) {
                    try {
                        sportType.setLogo(new Binary(sportTypeDto.getLogo().getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setMessage("Error processing image file");
                        return request;
                    }
                }
                baseService.populateCommonData(sportType);
                sportType.setStatus(true);
                sportTypeRepository.save(sportType);
                if (sportType.getRecordId() == null) {
                    sportType.setRecordId(String.valueOf(sportType.getId().getTimestamp()));
                }
                sportTypeRepository.save(sportType);
                request.setMessage("Data added Successfully");
            }

            sportTypeList.add(sportType);
            request.setBaseUrl(ADMIN_SPORTTYPE);
        }
        request.setSportTypeDtoList(modelMapper.map(sportTypeList, List.class));
        return request;
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
