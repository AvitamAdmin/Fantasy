package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WithdrawalMethodsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WithdrawalMethodsRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

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
    public WithdrawalMethodsDto handleEdit(WithdrawalMethodsDto request) {
        WithdrawalMethodsDto withdrawalMethodsDto = new WithdrawalMethodsDto();
        WithdrawalMethods withdrawalMethods = null;
        if(request.getRecordId()!=null){
            WithdrawalMethods requestData = request.getWithdrawalMethods();
            withdrawalMethods = withdrawalMethodsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, withdrawalMethods);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.WITHDRAWAL_METHODS,request.getWithdrawalMethods().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            withdrawalMethods=request.getWithdrawalMethods();
        }
        if(request.getLogo()!=null && !request.getLogo().isEmpty()) {
            try {
                withdrawalMethods.setLogo(new Binary(request.getLogo().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                withdrawalMethodsDto.setMessage("Error processing image file");
                return withdrawalMethodsDto;
            }
        }
        baseService.populateCommonData(withdrawalMethods);
        withdrawalMethodsRepository.save(withdrawalMethods);
        if(request.getRecordId()==null){
            withdrawalMethods.setRecordId(String.valueOf(withdrawalMethods.getId().getTimestamp()));
        }
        withdrawalMethodsRepository.save(withdrawalMethods);
        withdrawalMethodsDto.setWithdrawalMethods(withdrawalMethods);
        withdrawalMethodsDto.setBaseUrl(ADMIN_WITHDRAWALMETHODS);
        return withdrawalMethodsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        WithdrawalMethods WithdrawalMethodsOptional=withdrawalMethodsRepository.findByRecordId(recordId);
        if(WithdrawalMethodsOptional!=null)
        {
            withdrawalMethodsRepository.save(WithdrawalMethodsOptional);
        }
    }


}
