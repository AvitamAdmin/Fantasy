package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MobileTokenDto;
import com.avitam.fantasy11.api.dto.MobileTokenWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MobileTokenService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MobileTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MobileTokenServiceImpl implements MobileTokenService {

    @Autowired
    private MobileTokenRepository mobileTokenRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_MOBILETOKEN="/admin/mobileToken";

    @Override
    public MobileToken findByRecordId(String recordId) {

        return mobileTokenRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        mobileTokenRepository.deleteByRecordId(recordId);
    }

    @Override
    public MobileTokenWsDto handleEdit(MobileTokenWsDto request) {

        MobileToken mobileToken = null;
        List<MobileTokenDto> mobileTokenDtos = request.getMobileTokenDtoList();
        List<MobileToken>mobileTokens = new ArrayList<>();

        for(MobileTokenDto mobileTokenDto1 : mobileTokenDtos){
            if(mobileTokenDto1.getRecordId() != null){
                mobileToken = mobileTokenRepository.findByRecordId(mobileTokenDto1.getRecordId());
                modelMapper.map(mobileTokenDto1,mobileToken);
                mobileToken.setLastModified(new Date());
                mobileTokenRepository.save(mobileToken);
                request.setMessage("Data updated Successfully");
            }else{
                if(baseService.validateIdentifier(EntityConstants.MOBILE_TOKEN,mobileTokenDto1.getIdentifier()) !=null){
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                mobileToken = modelMapper.map(mobileTokenDto1,MobileToken.class);
                baseService.populateCommonData(mobileToken);
                mobileToken.setStatus(true);
                mobileTokenRepository.save(mobileToken);
                request.setMessage("MobileToken added successfully");

            }

            if (mobileToken.getRecordId()== null){
                mobileToken.setRecordId(String.valueOf(mobileToken.getId().getTimestamp()));
            }
            mobileTokenRepository.save(mobileToken);
            mobileTokens.add(mobileToken);
            request.setBaseUrl(ADMIN_MOBILETOKEN);

        }
        request.setMobileTokenDtoList(modelMapper.map(mobileTokens,List.class));
        return request;

    }

    @Override
    public void updateByRecordId(String recordId) {
        MobileToken mobileToken=mobileTokenRepository.findByRecordId(recordId);
        if(mobileToken!=null){

            mobileTokenRepository.save(mobileToken);
        }
    }

    @Override
    public String generateOtpForUser(String email) {
        return "";
    }
}
