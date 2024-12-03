package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PointsMasterDto;
import com.avitam.fantasy11.api.dto.PointsMasterWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PointsMasterService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PointsMaster;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PointsMasterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointsMasterServiceImpl implements PointsMasterService {
    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_POINTSMASTER = "/admin/pointsMaster";

    @Override
    public PointsMaster findByRecordId(String recordId) {
        return pointsMasterRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        pointsMasterRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        PointsMaster pointsMaster = pointsMasterRepository.findByRecordId(recordId);
        if (pointsMaster != null) {
            pointsMasterRepository.save(pointsMaster);
        }
    }

    @Override
    public PointsMasterWsDto handleEdit(PointsMasterWsDto request) {
        PointsMasterDto pointsMasterDto = new PointsMasterDto();
        List<PointsMasterDto> pointsMasterDtos = request.getPointsMasterDtos();
        List<PointsMaster> pointsMasterList=new ArrayList<>();
        PointsMaster pointsMaster = null;

        for (PointsMasterDto pointsMasterDto1 : pointsMasterDtos) {
            if (request.getRecordId() != null) {
                PointsMaster requestData = modelMapper.map(pointsMasterDto1, PointsMaster.class);
                pointsMaster = pointsMasterRepository.findByRecordId(request.getRecordId());
                modelMapper.map(requestData, pointsMaster);
            } else {
                if (baseService.validateIdentifier(EntityConstants.POINTS_MASTER, pointsMasterDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                pointsMaster = modelMapper.map(pointsMasterDto1, PointsMaster.class);
            }
            baseService.populateCommonData(pointsMaster);
            pointsMasterRepository.save(pointsMaster);
            if (request.getRecordId() == null) {
                pointsMaster.setRecordId(String.valueOf(pointsMaster.getId().getTimestamp()));
            }
            pointsMasterRepository.save(pointsMaster);
            pointsMasterList.add(pointsMaster);
            request.setBaseUrl(ADMIN_POINTSMASTER);
            request.setMessage("Data added Successfully");
        }
        request.setPointsMasterDtos(modelMapper.map(pointsMasterList,List.class));
        return request;
    }
}
