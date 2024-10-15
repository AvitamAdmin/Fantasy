package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.api.service.BannerService;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.repository.BannerRepository;
import com.avitam.fantasy11.repository.EntityConstants;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;
    @Autowired
    private BaseService baseService;
    public static final String ADMIN_BANNER = "/admin/banner";


    @Override
    public Banner findByRecordId(String recordId) {

        return bannerRepository.findByRecordId(recordId);
    }

    @Override
    public void deleteByRecordId(String recordId) {

        bannerRepository.deleteByRecordId(recordId);

    }

    @Override
    public BannerDto handleEdit(BannerDto request) {
        BannerDto bannerDto=new BannerDto();
        Banner banner=null;
        if (request.getRecordId()!=null){
            Banner requestData=request.getBanner();
            banner=bannerRepository.findByRecordId(request.getRecordId());
            modelMapper.map(requestData,banner);
        }else {
            if(baseService.validateIdentifier(EntityConstants.BANNER,request.getBanner().getIdentifier())!=null)
            {
                request.setSuccess(false);
                request.setMessage("Identifier already present");
                return request;
            }
            banner=request.getBanner();
        }
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                banner.setImage(new Binary(request.getImage().getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
                bannerDto.setMessage("Error processing image file");
                return bannerDto;
            }
        }
        baseService.populateCommonData(banner);
        bannerRepository.save(banner);
        if (request.getRecordId()==null){
            banner.setRecordId(String.valueOf(banner.getId().getTimestamp()));
        }
        bannerRepository.save(banner);
        bannerDto.setBanner(banner);
        bannerDto.setBaseUrl(ADMIN_BANNER);
        return bannerDto;
    }

    @Override
    public void updateByRecordId(String recordId) {

        Banner banner=bannerRepository.findByRecordId(recordId);
        if(banner!=null){
            bannerRepository.save(banner);
        }

    }
}
