package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.service.KycService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.model.KYCRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KycServiceImpl implements KycService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private KYCRepository kycRepository;

    @Override
    public KYC findByRecordId(String recordId) {
        return kycRepository.findByRecordId(recordId);
    }

    @Override
    public KYCDto handleEdit(KYCDto request) {
        KYCDto kycDto=new KYCDto();
        KYC kyc=null;
        if (request.getRecordId()!=null){
            KYC requestData= request.getKyc();
            kyc=kycRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,kyc);
        }else {
            kyc=request.getKyc();
            kyc.setCreator(coreService.getCurrentUser().getUsername());
            kyc.setCreationTime(new Date());
            kycRepository.save(kyc);
        }
        kyc.setLastModified(new Date());
        if (request.getRecordId()==null){
            kyc.setRecordId(String.valueOf(kyc.getId().getTimestamp()));
        }
        kycRepository.save(kyc);
        kycDto.setKyc(kyc);
        return kycDto;
    }

    @Override
    public void deleteByRecordId(String recordId) {

        kycRepository.deleteByRecordId(recordId);
    }

    @Override
    public void updateByRecordId(String recordId) {
        KYC kyc = kycRepository.findByRecordId(recordId);
        if (kyc != null) {

            kycRepository.save(kyc);
        }
    }

}
