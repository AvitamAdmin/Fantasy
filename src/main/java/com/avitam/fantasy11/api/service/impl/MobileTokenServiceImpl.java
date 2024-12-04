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
    private CoreService coreService;
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
        MobileTokenWsDto mobileTokenWsDto = new MobileTokenWsDto();
        MobileToken mobileToken = null;
        List<MobileTokenDto> mobileTokenDtos = request.getMobileTokenDtoList();
        List<MobileToken>mobileTokens = new ArrayList<>();
        MobileTokenDto mobileTokenDto = new MobileTokenDto();
        for(MobileTokenDto mobileTokenDto1 : mobileTokenDtos){
            if(mobileTokenDto1.getRecordId() != null){
                mobileToken = mobileTokenRepository.findByRecordId(mobileTokenDto1.getRecordId());
                modelMapper.map(mobileTokenDto1,mobileToken);
                mobileTokenRepository.save(mobileToken);
            }else{
                if(baseService.validateIdentifier(EntityConstants.MOBILE_TOKEN,mobileTokenDto1.getIdentifier()) !=null){
                    request.setSuccess(false);
                    request.setMessage("Identifier already present");
                    return request;
                }
                mobileToken = modelMapper.map(mobileTokenDto,MobileToken.class);
            }
            mobileTokenRepository.save(mobileToken);
            mobileToken.setLastModified(new Date());
            if (mobileToken.getRecordId()== null){
                mobileToken.setRecordId(String.valueOf(mobileToken.getId().getTimestamp()));
            }
            mobileTokenRepository.save(mobileToken);
            mobileTokens.add(mobileToken);
            mobileTokenWsDto.setBaseUrl(ADMIN_MOBILETOKEN);
            mobileTokenWsDto.setMessage("MobileToken was updated successfully");

        }
        mobileTokenWsDto.setMobileTokenDtoList(modelMapper.map(mobileTokens,List.class));
        return mobileTokenWsDto;

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
