package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.LineUpStatusDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.LineUpStatusService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.LineUpStatus;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.LineUpStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public LineUpStatusDto handleEdit(LineUpStatusDto request) {
        LineUpStatusDto lineUpStatusDto = new LineUpStatusDto();
        LineUpStatus lineUpStatus = null;
        if (request.getRecordId() != null) {
            LineUpStatus requestData = request.getLineUpStatus();
            lineUpStatus = lineUpStatusRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, lineUpStatus);
        } else {
            if(baseService.validateIdentifier(EntityConstants.LINEUPSTATUS,request.getLineUpStatus().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            lineUpStatus = request.getLineUpStatus();
        }
        baseService.populateCommonData(lineUpStatus);
        lineUpStatusRepository.save(lineUpStatus);
        if (request.getRecordId() == null) {
            lineUpStatus.setRecordId(String.valueOf(lineUpStatus.getId().getTimestamp()));
        }
        lineUpStatusRepository.save(lineUpStatus);
        lineUpStatusDto.setLineUpStatus(lineUpStatus);
        lineUpStatusDto.setBaseUrl(ADMIN_LINEUP_STATUS);
        return lineUpStatusDto;
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
