package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.MobileTokenDto;
import com.avitam.fantasy11.api.dto.UserDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.MobileTokenService;
import com.avitam.fantasy11.core.Utility;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.model.User;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.MobileTokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MobileTokenDto handleEdit(MobileTokenDto request) {
        MobileTokenDto mobileTokenDto=new MobileTokenDto();
        MobileToken mobileToken=null;
        if(request.getRecordId()!=null){
            MobileToken requestData=request.getMobileToken();
            mobileToken=mobileTokenRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,mobileToken);
        }else{
            if(baseService.validateIdentifier(EntityConstants.MOBILE_TOKEN,request.getMobileToken().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            mobileToken=request.getMobileToken();
            mobileToken.setOtp(Utility.OtpUtil.generateOtp(4));
        }
       baseService.populateCommonData(mobileToken);
        mobileTokenRepository.save(mobileToken);
        if (request.getRecordId()==null){
            mobileToken.setRecordId(String.valueOf(mobileToken.getId().getTimestamp()));
        }
        mobileTokenRepository.save(mobileToken);
        mobileTokenDto.setMobileToken(mobileToken);
        mobileTokenDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        MobileToken mobileToken=mobileTokenRepository.findByRecordId(recordId);
        if(mobileToken!=null){

            mobileTokenRepository.save(mobileToken);
        }
    }

   @Override
    public  String generateOtpForUser(String email) {
        MobileToken mobileToken = mobileTokenRepository.findByEmail(email);
        if (mobileToken == null) {
            throw new RuntimeException("User not found");
        }

        String otp = Utility.OtpUtil.generateOtp(4);  // Generate a 4-digit OTP
        mobileToken.setOtp(otp);                     // Set OTP in the user entity
        mobileTokenRepository.save(mobileToken);            // Save the user with OTP in the database

        MobileTokenDto mobileTokenDto = new MobileTokenDto();
        mobileTokenDto.getMobileToken().setId(mobileToken.getId());
        mobileTokenDto.getMobileToken().setUsername(mobileToken.getUsername());
        mobileTokenDto.getMobileToken().setEmail(mobileToken.getEmail());
        mobileTokenDto.setOtp(otp);                  // Set OTP in the response DTO

        return otp;
    }
}
