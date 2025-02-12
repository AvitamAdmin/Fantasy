package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.OtpDto;
import com.avitam.fantasy11.api.dto.OtpWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.OtpService;
import com.avitam.fantasy11.model.OTP;
import com.avitam.fantasy11.repository.OtpRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OtpServiceImpl implements OtpService {
    public static final String ADMIN_OTP = "/admin/otp";
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public OtpWsDto handelEdit(OtpWsDto otpWsDto) {
        OTP otp = null;
        List<OtpDto> otpDtoList = otpWsDto.getOtpDtoList();
        List<OTP> otpList = new ArrayList<>();
        for (OtpDto otpDto : otpDtoList) {
            if (otpDto.getRecordId() != null) {
                otp = otpRepository.findByRecordId(otpDto.getRecordId());
            }

        }
        return null;
    }
}
