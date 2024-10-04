package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestDto;
import com.avitam.fantasy11.api.dto.SportTypeDto;
import com.avitam.fantasy11.api.service.SportTypeService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Contest;
import com.avitam.fantasy11.model.SportType;
import com.avitam.fantasy11.model.SportTypeRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class SportTypeServiceImpl implements SportTypeService {
   @Autowired
   private SportTypeRepository sportTypeRepository;
   @Autowired
   private ModelMapper modelMapper;
   @Autowired
   private CoreService coreService;

   private static final String ADMIN_SPORTTYPE="/admin/sportType";

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
            sportType = request.getSportType();
            sportType.setCreator(coreService.getCurrentUser().getUsername());
            sportType.setCreationTime(new Date());
            sportTypeRepository.save(sportType);
            if (request.getLogo() != null && !request.getLogo().isEmpty()) {
                try {
                    sportType.setLogo(new Binary(request.getLogo().getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                    sportTypeDto.setMessage("Error processing image file");
                    return sportTypeDto;
                }
            }
        }
            sportType.setLastModified(new Date());
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
        SportType sportType=sportTypeRepository.findByRecordId(recordId);
        if(sportType!=null){

            sportTypeRepository.save(sportType);
        }
    }
}
