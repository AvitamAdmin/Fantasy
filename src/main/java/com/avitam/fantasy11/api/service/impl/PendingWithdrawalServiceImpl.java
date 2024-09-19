package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.PendingWithdrawalDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.api.service.PendingWithdrawalService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.AddressRepository;
import com.avitam.fantasy11.model.PendingWithdrawal;
import com.avitam.fantasy11.model.PendingWithdrawalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PendingWithdrawalServiceImpl implements PendingWithdrawalService {

    @Autowired
    private PendingWithdrawalRepository pendingWithdrawalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    public static final String ADMIN_PENDINGWITHDRAWAL = "/admin/pendingWithdrawal";

    @Override
    public PendingWithdrawal findByRecordId(String recordId) {
        return pendingWithdrawalRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {
        pendingWithdrawalRepository.deleteByRecordId(recordId);
    }

    @Override
    public PendingWithdrawalDto handleEdit(PendingWithdrawalDto request) {
        PendingWithdrawalDto pendingWithdrawalDto = new PendingWithdrawalDto();
        PendingWithdrawal pendingWithdrawal = null;
        if(request.getRecordId()!=null){
            PendingWithdrawal requestData = request.getPendingWithdrawal();
            pendingWithdrawal = pendingWithdrawalRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData, pendingWithdrawal);
        }
        else {
            pendingWithdrawal=request.getPendingWithdrawal();
            pendingWithdrawal.setCreator(coreService.getCurrentUser().getUsername());
            pendingWithdrawal.setCreationTime(new Date());
            pendingWithdrawalRepository.save(pendingWithdrawal);
        }
        pendingWithdrawal.setLastModified(new Date());
        if(request.getRecordId()==null){
            pendingWithdrawal.setRecordId(String.valueOf(pendingWithdrawal.getId().getTimestamp()));
        }
        pendingWithdrawalRepository.save(pendingWithdrawal);
        pendingWithdrawalDto.setPendingWithdrawal(pendingWithdrawal);
        pendingWithdrawalDto.setBaseUrl(ADMIN_PENDINGWITHDRAWAL);
        return pendingWithdrawalDto;
    }

    @Override
    public void updateByRecordId(String recordId) {
        PendingWithdrawal  pendingWithdrawalOptional=pendingWithdrawalRepository.findByRecordId(recordId);
        if(pendingWithdrawalOptional!=null)
        {
            pendingWithdrawalRepository.save(pendingWithdrawalOptional);
        }
    }


}
