package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MobileTokenDto;
import com.avitam.fantasy11.api.service.MobileTokenService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.model.MobileTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MobileTokenServiceImpl implements MobileTokenService {

    @Autowired
    private MobileTokenRepository mobileTokenRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @Override
    public MobileToken findByRecordId(String recordId) {

        return mobileTokenRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        mobileTokenRepository.deleteByRecordId(recordId);
    }

    @Override
    public MobileTokenDto handleEdit(MobileTokenDto request) {
        MobileTokenDto mobileTokenDto=new MobileTokenDto();
        MobileToken mobileToken=null;
        if(request.getRecordId()!=null){
            MobileToken requestData=request.getMobileToken();
            mobileToken=mobileTokenRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,mobileToken);
        }else{
            mobileToken=request.getMobileToken();
            mobileToken.setCreator(coreService.getCurrentUser().getName());
            mobileToken.setCreationTime(new Date());
            mobileTokenRepository.save(mobileToken);
        }
        mobileToken.setLastModified(new Date());
        if (request.getRecordId()==null){
            mobileToken.setRecordId(String.valueOf(mobileToken.getId().getTimestamp()));
        }
        mobileTokenRepository.save(mobileToken);
        mobileTokenDto.setMobileToken(mobileToken);
        return mobileTokenDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        MobileToken mobileToken=mobileTokenRepository.findByRecordId(recordId);
        if(mobileToken!=null){

            mobileTokenRepository.save(mobileToken);
        }
    }
}
