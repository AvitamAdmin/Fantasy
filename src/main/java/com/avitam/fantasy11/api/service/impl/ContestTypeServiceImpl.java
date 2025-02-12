package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.ContestTypeDto;
import com.avitam.fantasy11.api.dto.ContestTypeWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.ContestTypeService;
import com.avitam.fantasy11.model.ContestType;
import com.avitam.fantasy11.repository.ContestTypeRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContestTypeServiceImpl implements ContestTypeService {

    private static final String ADMIN_MAINCONTEST = "/admin/contestType";
    @Autowired
    private ContestTypeRepository contestTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public ContestType findByRecordId(String recordId) {
        return contestTypeRepository.findByRecordId(recordId);
    }

    @Override
    public ContestTypeWsDto handleEdit(ContestTypeWsDto request) {
        ContestType contestTypeData = null;
        List<ContestTypeDto> contestTypeDtos = request.getContestTypeDtoList();
        List<ContestType> contestTypeList = new ArrayList<>();

        for (ContestTypeDto contestTypeDto1 : contestTypeDtos) {
            if (contestTypeDto1.getRecordId() != null) {
                contestTypeData = contestTypeRepository.findByRecordId(contestTypeDto1.getRecordId());
                modelMapper.map(contestTypeDto1, contestTypeData);
                contestTypeRepository.save(contestTypeData);
                request.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.MAINCONTEST, contestTypeDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }

                contestTypeData = modelMapper.map(contestTypeDto1, ContestType.class);
            }
            baseService.populateCommonData(contestTypeData);
            contestTypeData.setStatus(true);
            contestTypeRepository.save(contestTypeData);
            if (contestTypeData.getRecordId() == null) {
                contestTypeData.setRecordId(String.valueOf(contestTypeData.getId().getTimestamp()));
            }
            contestTypeRepository.save(contestTypeData);
            contestTypeList.add(contestTypeData);
            request.setMessage("Data added successfully");
            request.setBaseUrl(ADMIN_MAINCONTEST);

        }
        request.setContestTypeDtoList(modelMapper.map(contestTypeList, List.class));
        return request;
    }


    @Override
    public void deleteByRecordId(String recordId) {
        contestTypeRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        ContestType contestType = contestTypeRepository.findByRecordId(recordId);
        if (contestType != null) {
            contestTypeRepository.save(contestType);
        }
    }


}

