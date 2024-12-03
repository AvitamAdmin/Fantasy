package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WebsiteSettingDto;
import com.avitam.fantasy11.api.dto.WebsiteSettingsWsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.WebsiteSetting;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WithdrawalDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WithdrawalDetailsServiceImpl implements WithdrawalDetailsService {

    @Autowired
    private WithdrawalDetailsRepository withdrawalDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_WITHDRAWALDETAILS = "/admin/withdrawalDetails";

    @Override
    public WithdrawalDetails findByRecordId(String recordId) {
        return withdrawalDetailsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        withdrawalDetailsRepository.deleteByRecordId(recordId);
    }

    @Override
    public WithdrawalDetailsWsDto handleEdit(WithdrawalDetailsWsDto request) {
        WithdrawalDetailsWsDto withdrawalDetailsWsDto = new WithdrawalDetailsWsDto();
        WithdrawalDetails withdrawalDetailsData = null;
        List<WithdrawalDetailsDto> withdrawalDetailsDtos = request.getWithdrawalDetailsDtoList();
        List<WithdrawalDetails> withdrawalDetailsList = new ArrayList<>();
        WithdrawalDetailsDto withdrawalDetailsDto = new WithdrawalDetailsDto();
        for (WithdrawalDetailsDto withdrawalDetailsDto1 : withdrawalDetailsDtos) {
            if (withdrawalDetailsDto1.getRecordId() != null) {
                withdrawalDetailsData = withdrawalDetailsRepository.findByRecordId(withdrawalDetailsDto1.getRecordId());
                modelMapper.map(withdrawalDetailsDto1, withdrawalDetailsData);
                withdrawalDetailsRepository.save(withdrawalDetailsData);
            } else {
                if (baseService.validateIdentifier(EntityConstants.KYC, withdrawalDetailsDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }

                withdrawalDetailsData = modelMapper.map(withdrawalDetailsDto, WithdrawalDetails.class);
            }
            withdrawalDetailsRepository.save(withdrawalDetailsData);
            withdrawalDetailsData.setLastModified(new Date());
            if (withdrawalDetailsData.getRecordId() == null) {
                withdrawalDetailsData.setRecordId(String.valueOf(withdrawalDetailsData.getId().getTimestamp()));
            }
            withdrawalDetailsRepository.save(withdrawalDetailsData);
            withdrawalDetailsList.add(withdrawalDetailsData);
            withdrawalDetailsWsDto.setMessage("Withdrawal Details was updated successfully");
            withdrawalDetailsWsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);

        }
        withdrawalDetailsWsDto.setWithdrawalDetailsDtoList(modelMapper.map(withdrawalDetailsList, List.class));
        return withdrawalDetailsWsDto;
    }


    @Override
    public void updateByRecordId(String recordId) {
        WithdrawalDetails withdrawalDetailsOptional = withdrawalDetailsRepository.findByRecordId(recordId);
        if (withdrawalDetailsOptional != null) {
            withdrawalDetailsRepository.save(withdrawalDetailsOptional);
        }
    }


}
