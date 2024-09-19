package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.api.service.WithdrawalMethodsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.AddressRepository;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.model.WithdrawalMethodsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WithdrawalMethodsServiceImpl implements WithdrawalMethodsService {

    @Autowired
    private WithdrawalMethodsRepository withdrawalMethodsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
            withdrawalMethods=request.getWithdrawalMethods();
            withdrawalMethods.setCreator(coreService.getCurrentUser().getUsername());
            withdrawalMethods.setCreationTime(new Date());
            withdrawalMethodsRepository.save(withdrawalMethods);
        }
        withdrawalMethods.setLastModified(new Date());
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
