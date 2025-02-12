package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LineUpStatusDto;
import com.avitam.fantasy11.api.dto.LineUpStatusWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LineUpStatusService;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LineUpStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class LineUpStatusImpl implements LineUpStatusService {

    private static final String ADMIN_LINEUP_STATUS = "/admin/lineupStatus";
    @Autowired
    private LineUpStatusRepository lineUpStatusRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;


    @Override
    public LineUpStatus findByRecordId(String recordId) {
        return lineUpStatusRepository.findByRecordId(recordId);
    }

    @Override
    public LineUpStatusWsDto handleEdit(LineUpStatusWsDto request) {
        LineUpStatus lineUpStatusData = null;
        List<LineUpStatusDto> lineUpStatusDtos = request.getLineUpStatusDtoList();
        List<LineUpStatus> lineUpStatusList = new ArrayList<>();

        for (LineUpStatusDto lineUpStatusDto1 : lineUpStatusDtos) {
            if (lineUpStatusDto1.getRecordId() != null) {
                lineUpStatusData = lineUpStatusRepository.findByRecordId(lineUpStatusDto1.getRecordId());
                modelMapper.map(lineUpStatusDto1, lineUpStatusData);
                lineUpStatusData.setLastModified(new Date());
                lineUpStatusRepository.save(lineUpStatusData);
                request.setMessage("Lineup Status was updated successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.LINEUPSTATUS, lineUpStatusDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                lineUpStatusData = modelMapper.map(lineUpStatusDto1, LineUpStatus.class);
                baseService.populateCommonData(lineUpStatusData);
                lineUpStatusData.setStatus(true);
                lineUpStatusRepository.save(lineUpStatusData);
                if (lineUpStatusData.getRecordId() == null) {
                    lineUpStatusData.setRecordId(String.valueOf(lineUpStatusData.getId().getTimestamp()));
                }
                lineUpStatusRepository.save(lineUpStatusData);
                request.setMessage("Lineup Status added successfully");
            }
            lineUpStatusList.add(lineUpStatusData);
        }
        request.setBaseUrl(ADMIN_LINEUP_STATUS);
        request.setLineUpStatusDtoList(modelMapper.map(lineUpStatusList, List.class));
        return request;
    }


    @Override
    public void deleteByRecordId(String recordId) {
        lineUpStatusRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        LineUpStatus lineUpStatus = lineUpStatusRepository.findByRecordId(recordId);
        if (lineUpStatus != null) {
            lineUpStatusRepository.save(lineUpStatus);

        }

    }

}
