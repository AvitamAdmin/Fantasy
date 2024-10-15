package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.api.service.KycService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.repository.EntityConstants;
import com.avitam.fantasy11.repository.KYCRepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KycServiceImpl implements KycService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private BaseService baseService;

    private static final String ADMIN_KYC="/admin/kyc";

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
            if(baseService.validateIdentifier(EntityConstants.KYC,request.getKyc().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            kyc=request.getKyc();
            if(request.getPanImage()!=null && !request.getPanImage().isEmpty()){
                try{
                    kyc.setPanImage(new Binary(request.getPanImage().getBytes()));
                }catch(IOException e){
                    e.printStackTrace();
                    kycDto.setMessage("Error processing image file");
                    return kycDto;
                }
            }
        }
        baseService.populateCommonData(kyc);
        kycRepository.save(kyc);
        if (request.getRecordId()==null){
            kyc.setRecordId(String.valueOf(kyc.getId().getTimestamp()));
        }
        kycRepository.save(kyc);
        kycDto.setKyc(kyc);
        kycDto.setBaseUrl(ADMIN_KYC);
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
