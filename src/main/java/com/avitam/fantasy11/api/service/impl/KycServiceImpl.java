package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.dto.KYCWsDto;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final String ADMIN_KYC = "/admin/kyc";

    @Override
    public KYC findByRecordId(String recordId) {

        return kycRepository.findByRecordId(recordId);
    }

    @Override
    public KYCWsDto handleEdit(KYCWsDto request) {
        KYC kycData = null;
        List<KYCDto> kycDtos = request.getKycDtoList();
        List<KYC> kycList = new ArrayList<>();
        for (KYCDto kycDto1 : kycDtos) {
            if (kycDto1.getRecordId() != null) {
                kycData = kycRepository.findByRecordId(kycDto1.getRecordId());
                modelMapper.map(kycDto1, kycData);
                kycData.setLastModified(new Date());
                kycRepository.save(kycData);
                request.setMessage("KYC updated successfully");

            } else {
//                if (baseService.validateIdentifier(EntityConstants.KYC, kycDto1.getIdentifier()) != null) {
//                    request.setSuccess(false);
//                    request.setMessage("Identifier already present");
//                    return request;
//                }
                kycData = modelMapper.map(kycDto1, KYC.class);
                if (kycDto1.getPanImage() != null) {
                    try {
                        kycData.setPanImage(new Binary(kycDto1.getPanImage().getBytes()));
                    } catch (IOException e) {
                        request.setMessage("Error processing image file");
                        return request;
                    }
                }
                kycData.setCreationTime(new Date());
                kycData.setStatus(true);
                kycRepository.save(kycData);
                if (kycData.getRecordId() == null) {
                    kycData.setRecordId(String.valueOf(kycData.getId().getTimestamp()));
                }
                kycRepository.save(kycData);
                request.setMessage("KYC added Successfully");
            }

            kycList.add(kycData);
            request.setBaseUrl(ADMIN_KYC);
        }
        request.setKycDtoList(modelMapper.map(kycList, List.class));
        return request;
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
