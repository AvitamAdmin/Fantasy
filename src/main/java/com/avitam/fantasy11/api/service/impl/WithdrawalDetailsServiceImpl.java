package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WithdrawalDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WithdrawalDetailsServiceImpl implements WithdrawalDetailsService {

    @Autowired
    private WithdrawalDetailsRepository withdrawalDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;
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

        WithdrawalDetails withdrawalDetailsData = null;
        List<WithdrawalDetailsDto> withdrawalDetailsDtos = request.getWithdrawalDetailsDtoList();
        List<WithdrawalDetails> withdrawalDetailsList = new ArrayList<>();

        for (WithdrawalDetailsDto withdrawalDetailsDto1 : withdrawalDetailsDtos) {
            if (withdrawalDetailsDto1.getRecordId() != null) {
                withdrawalDetailsData = withdrawalDetailsRepository.findByRecordId(withdrawalDetailsDto1.getRecordId());
                modelMapper.map(withdrawalDetailsDto1, withdrawalDetailsData);
                withdrawalDetailsRepository.save(withdrawalDetailsData);
                request.setMessage("Data Updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.WITHDRAWAL_DETAILS, withdrawalDetailsDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }
                withdrawalDetailsData = modelMapper.map(withdrawalDetailsDto1, WithdrawalDetails.class);
            }
            baseService.populateCommonData(withdrawalDetailsData);
            withdrawalDetailsData.setStatus(true);
            withdrawalDetailsRepository.save(withdrawalDetailsData);
            if (withdrawalDetailsData.getRecordId() == null) {
                withdrawalDetailsData.setRecordId(String.valueOf(withdrawalDetailsData.getId().getTimestamp()));
            }
            withdrawalDetailsRepository.save(withdrawalDetailsData);
            withdrawalDetailsList.add(withdrawalDetailsData);
            request.setMessage("Data Added Successfully");
            request.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        }
        request.setWithdrawalDetailsDtoList(modelMapper.map(withdrawalDetailsList, List.class));
        return request;
    }

    @Override
    public void updateByRecordId(String recordId) {
        WithdrawalDetails withdrawalDetailsOptional = withdrawalDetailsRepository.findByRecordId(recordId);
        if (withdrawalDetailsOptional != null) {
            withdrawalDetailsRepository.save(withdrawalDetailsOptional);
        }
    }
}
