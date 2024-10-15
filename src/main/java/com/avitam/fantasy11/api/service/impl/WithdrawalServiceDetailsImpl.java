package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.WithdrawalDetailsDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.WithdrawalDetailsService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.WithdrawalDetails;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.WithdrawalDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceDetailsImpl implements WithdrawalDetailsService {

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
    public WithdrawalDetailsDto handleEdit(WithdrawalDetailsDto request) {
        WithdrawalDetailsDto withdrawalDetailsDto = new WithdrawalDetailsDto();
        WithdrawalDetails withdrawalDetails = null;
        if(request.getRecordId()!=null){
            WithdrawalDetails requestData = request.getWithdrawalDetails();
            withdrawalDetails = withdrawalDetailsRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, withdrawalDetails);
        }
        else {
            if(baseService.validateIdentifier(EntityConstants.WITHDRAWAL_DETAILS,request.getWithdrawalDetails().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            withdrawalDetails=request.getWithdrawalDetails();
        }
        baseService.populateCommonData(withdrawalDetails);
        withdrawalDetailsRepository.save(withdrawalDetails);
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
