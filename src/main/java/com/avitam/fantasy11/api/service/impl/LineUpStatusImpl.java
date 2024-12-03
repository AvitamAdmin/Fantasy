package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LeaderBoardDto;
import com.avitam.fantasy11.api.dto.LeaderBoardWsDto;
import com.avitam.fantasy11.api.dto.LineUpStatusDto;
import com.avitam.fantasy11.api.dto.LineUpStatusWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LineUpStatusService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.LeaderBoard;
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

    @Autowired
    private LineUpStatusRepository lineUpStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CoreService coreService;

    private static final String ADMIN_LINEUP_STATUS ="/admin/lineupStatus";

    @Autowired
    private BaseService baseService;


    @Override
    public LineUpStatus findByRecordId(String recordId) {
        return lineUpStatusRepository.findByRecordId(recordId);
    }

    @Override
    public LineUpStatusWsDto handleEdit(LineUpStatusWsDto request) {
        LineUpStatusWsDto lineUpStatusWsDto = new LineUpStatusWsDto();
        LineUpStatus lineUpStatusData = null;
        List<LineUpStatusDto> lineUpStatusDtos = request.getLineUpStatusDtoList();
        List<LineUpStatus> lineUpStatusList = new ArrayList<>();
        LineUpStatusDto lineUpStatusDto = new LineUpStatusDto();
        for (LineUpStatusDto lineUpStatusDto1 : lineUpStatusDtos) {
            if (lineUpStatusDto1.getRecordId() != null) {
                lineUpStatusData = lineUpStatusRepository.findByRecordId(lineUpStatusDto1.getRecordId());
                modelMapper.map(lineUpStatusDto1, lineUpStatusData);
                lineUpStatusRepository.save(lineUpStatusData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.KYC, lineUpStatusDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    //request.setMessage("Identifier already present");
                    return request;
                }

                lineUpStatusData = modelMapper.map(lineUpStatusDto, LineUpStatus.class);
            }
            lineUpStatusRepository.save(lineUpStatusData);
            lineUpStatusData.setLastModified(new Date());
            if (lineUpStatusData.getRecordId() == null) {
                lineUpStatusData.setRecordId(String.valueOf(lineUpStatusData.getId().getTimestamp()));
            }
            lineUpStatusRepository.save(lineUpStatusData);
            lineUpStatusList.add(lineUpStatusData);
            lineUpStatusWsDto.setMessage("Lineup Status was updated successfully");
            lineUpStatusWsDto.setBaseUrl(ADMIN_LINEUP_STATUS);

        }
        lineUpStatusWsDto.setLineUpStatusDtoList(modelMapper.map(lineUpStatusList, List.class));
        return lineUpStatusWsDto;
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
