package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.PointsUpdateService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.PointsUpdateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class PointsUpdateServiceImpl implements PointsUpdateService {
    @Autowired
    private PointsUpdateRepository pointsUpdateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_POINTSUPDATE="/admin/pointsUpdate";

    @Override
    public PointsUpdate findByRecordId(String recordId) {
        return pointsUpdateRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        pointsUpdateRepository.deleteByRecordId(recordId);
    }

    @Override
    public PointsUpdateDto handleEdit(PointsUpdateDto request) {
        PointsUpdateDto pointsUpdateDto=new PointsUpdateDto();
        PointsUpdate pointsUpdate=null;
        if (request.getRecordId()!=null){
            PointsUpdate requestData=request.getPointsUpdate();
            pointsUpdate=pointsUpdateRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,pointsUpdate);
        }else {
            if(baseService.validateIdentifier(EntityConstants.POINTS_UPDATE,request.getPointsUpdate().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            pointsUpdate=request.getPointsUpdate();
        }
        baseService.populateCommonData(pointsUpdate);
        pointsUpdateRepository.save(pointsUpdate);
        if (request.getRecordId()==null){
            pointsUpdate.setRecordId(String.valueOf(pointsUpdate.getId().getTimestamp()));
        }
        pointsUpdateRepository.save(pointsUpdate);
        pointsUpdateDto.setPointsUpdate(pointsUpdate);
        pointsUpdateDto.setBaseUrl(ADMIN_POINTSUPDATE);
        return pointsUpdateDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        PointsUpdate  pointsUpdate=pointsUpdateRepository.findByRecordId(recordId);
        if(pointsUpdate !=null){
            pointsUpdateRepository.save(pointsUpdate);
        }

    }
}
