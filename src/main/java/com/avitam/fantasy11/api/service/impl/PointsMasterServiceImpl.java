package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.PointsMasterDto;
import com.avitam.fantasy11.api.service.PointsMasterService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.PointsMaster;
import com.avitam.fantasy11.model.PointsMasterRepository;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PointsMasterServiceImpl implements PointsMasterService {
    @Autowired
    private PointsMasterRepository pointsMasterRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
        PointsMaster pointsMasterOptional=pointsMasterRepository.findByRecordId(recordId);
        //pointsMasterOptional.ifPresent(pointsMaster -> pointsMasterRepository.save(pointsMaster));
    }

    @Override
    public PointsMasterDto handleEdit(PointsMasterDto request) {
        PointsMasterDto pointsMasterDto = new PointsMasterDto();
        PointsMaster pointsMaster = null;
        if(request.getRecordId()!=null){
            PointsMaster requestData = request.getPointsMaster();
            pointsMaster = pointsMasterRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, pointsMaster);
        }
        else{
            pointsMaster = request.getPointsMaster();
            pointsMaster.setCreator(coreService.getCurrentUser().getUsername());
            pointsMaster.setCreationTime(new Date());
            pointsMasterRepository.save(pointsMaster);
        }
        pointsMaster.setLastModified(new Date());
        if(request.getRecordId()==null){
            pointsMaster.setRecordId(String.valueOf(pointsMaster.getId().getTimestamp()));
        }
        pointsMasterRepository.save(pointsMaster);
        pointsMasterDto.setPointsMaster(pointsMaster);
        pointsMasterDto.setBaseUrl(ADMIN_POINTSMASTER);
        return pointsMasterDto;
    }
}
