package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.api.dto.PointsUpdateWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PointsUpdateService;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PointsUpdateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PointsUpdateServiceImpl implements PointsUpdateService {
    @Autowired
    private PointsUpdateRepository pointsUpdateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_POINTSUPDATE = "/admin/pointsUpdate";

    @Override
    public PointsUpdate findByRecordId(String recordId) {
        return pointsUpdateRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        pointsUpdateRepository.deleteByRecordId(recordId);
    }

    @Override
    public PointsUpdateWsDto handleEdit(PointsUpdateWsDto request) {

        List<PointsUpdate> pointsUpdates = new ArrayList<>();
        PointsUpdate pointsUpdate = new PointsUpdate();
        List<PointsUpdateDto> pointsUpdateDtos = request.getPointsUpdateDtoList();

        for (PointsUpdateDto pointsUpdateDto1 : pointsUpdateDtos) {
            if (pointsUpdateDto1.getRecordId() != null) {
                PointsUpdate requestData = modelMapper.map(pointsUpdateDto1, PointsUpdate.class);
                pointsUpdate = pointsUpdateRepository.findByRecordId(pointsUpdateDto1.getRecordId());
                modelMapper.map(requestData, pointsUpdate);
            } else {
                if (baseService.validateIdentifier(EntityConstants.POINTS_UPDATE, pointsUpdate.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                pointsUpdate = modelMapper.map(pointsUpdateDto1, PointsUpdate.class);
            }
            //baseService.populateCommonData(pointsUpdate);
            pointsUpdate.setStatus(true);
            pointsUpdateRepository.save(pointsUpdate);
            if (request.getRecordId() == null) {
                pointsUpdate.setRecordId(String.valueOf(pointsUpdate.getId().getTimestamp()));
            }
            pointsUpdateRepository.save(pointsUpdate);
            pointsUpdates.add(pointsUpdate);
            request.setBaseUrl(ADMIN_POINTSUPDATE);
        }
        request.setPointsUpdateDtoList(modelMapper.map(pointsUpdates, List.class));
        return request;
    }

    @Override
    public void updateByRecordId(String recordId) {
        PointsUpdate pointsUpdate = pointsUpdateRepository.findByRecordId(recordId);
        if (pointsUpdate != null) {
            pointsUpdateRepository.save(pointsUpdate);
        }

    }
}
