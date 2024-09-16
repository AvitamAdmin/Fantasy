package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PointsUpdateDto;
import com.avitam.fantasy11.api.service.PointsUpdateService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PointsUpdate;
import com.avitam.fantasy11.model.PointsUpdateRepository;
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
            pointsUpdate=request.getPointsUpdate();
            pointsUpdate.setCreator(coreService.getCurrentUser().getUsername());
            pointsUpdate.setCreationTime(new Date());
            pointsUpdateRepository.save(pointsUpdate);
        }
        pointsUpdate.setLastModified(new Date());
        if (request.getRecordId()==null){
            pointsUpdate.setRecordId(String.valueOf(pointsUpdate.getId().getTimestamp()));
        }
        pointsUpdateRepository.save(pointsUpdate);
        pointsUpdateDto.setPointsUpdate(pointsUpdate);
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
