package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalDetailsWsDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsWsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WithdrawalMethodsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WithdrawalMethodsRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WithdrawalMethodsServiceImpl implements WithdrawalMethodsService {

    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;

    public static final String ADMIN_WITHDRAWALMETHODS = "/admin/withdrawalMethods";

    @Override
    public WithdrawalMethods findByRecordId(String recordId) {
        return withdrawalMethodsRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        withdrawalMethodsRepository.deleteByRecordId(recordId);
    }


    @Override
    public WithdrawalMethodsWsDto handleEdit(WithdrawalMethodsWsDto request) {
        WithdrawalMethods withdrawalMethodsData = null;
        List<WithdrawalMethodsDto> withdrawalMethodsDtos = request.getWithdrawalMethodsDtoList();
        List<WithdrawalMethods> withdrawalMethodsList = new ArrayList<>();
        for (WithdrawalMethodsDto withdrawalMethodsDto1 : withdrawalMethodsDtos) {
            if (withdrawalMethodsDto1.getRecordId() != null) {
                withdrawalMethodsData = withdrawalMethodsRepository.findByRecordId(withdrawalMethodsDto1.getRecordId());
                modelMapper.map(withdrawalMethodsDto1, withdrawalMethodsData);
                withdrawalMethodsRepository.save(withdrawalMethodsData);
                request.setMessage("Data Updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.WITHDRAWAL_METHODS, withdrawalMethodsDto1.getIdentifier()) != null) {
                    request.setSuccess(false);
                    request.setMessage("already present");
                    return request;
                }

                withdrawalMethodsData = modelMapper.map(withdrawalMethodsDto1, WithdrawalMethods.class);
            }

            baseService.populateCommonData(withdrawalMethodsData);
            withdrawalMethodsData.setStatus(true);
            withdrawalMethodsRepository.save(withdrawalMethodsData);
            if (withdrawalMethodsData.getRecordId() == null) {
                withdrawalMethodsData.setRecordId(String.valueOf(withdrawalMethodsData.getId().getTimestamp()));
            }
            withdrawalMethodsRepository.save(withdrawalMethodsData);
            withdrawalMethodsList.add(withdrawalMethodsData);
            request.setMessage("Withdrawal Details added successfully");
            request.setBaseUrl(ADMIN_WITHDRAWALMETHODS);

        }
        request.setWithdrawalMethodsDtoList(modelMapper.map(withdrawalMethodsList, List.class));
        return request;
    }


    @Override
    public void updateByRecordId(String recordId) {
        WithdrawalMethods WithdrawalMethodsOptional = withdrawalMethodsRepository.findByRecordId(recordId);
        if (WithdrawalMethodsOptional != null) {
            withdrawalMethodsRepository.save(WithdrawalMethodsOptional);
        }
    }


}
