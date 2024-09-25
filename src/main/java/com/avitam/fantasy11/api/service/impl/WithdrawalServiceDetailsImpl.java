package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.dto.WithdrawalMethodsDto;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.api.service.WithdrawalMethodsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.model.WithdrawalDetailsRepository;
import com.avitam.fantasy11.model.WithdrawalMethods;
import com.avitam.fantasy11.model.WithdrawalMethodsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WithdrawalServiceDetailsImpl implements WithdrawalDetailsService {

    @Autowired
    private WithdrawalDetailsRepository withdrawalDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

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
    public WithdrawalDetailsDto handleEdit(WithdrawalDetailsDto request) {
        WithdrawalDetailsDto withdrawalDetailsDto = new WithdrawalDetailsDto();
        WithdrawalDetails withdrawalDetails = null;
        if(request.getRecordId()!=null){
            WithdrawalDetails requestData = request.getWithdrawalDetails();
            withdrawalDetails = withdrawalDetailsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, withdrawalDetails);
        }
        else {
            withdrawalDetails=request.getWithdrawalDetails();
            withdrawalDetails.setCreator(coreService.getCurrentUser().getUsername());
            withdrawalDetails.setCreationTime(new Date());
            withdrawalDetailsRepository.save(withdrawalDetails);
        }
        withdrawalDetails.setLastModified(new Date());
        if(request.getRecordId()==null){
            withdrawalDetails.setRecordId(String.valueOf(withdrawalDetails.getId().getTimestamp()));
        }
        withdrawalDetailsRepository.save(withdrawalDetails);
        withdrawalDetailsDto.setWithdrawalDetails(withdrawalDetails);
        withdrawalDetailsDto.setBaseUrl(ADMIN_WITHDRAWALDETAILS);
        return withdrawalDetailsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        WithdrawalDetails withdrawalDetailsOptional=withdrawalDetailsRepository.findByRecordId(recordId);
        if(withdrawalDetailsOptional!=null)
        {
            withdrawalDetailsRepository.save(withdrawalDetailsOptional);
        }
    }


}
