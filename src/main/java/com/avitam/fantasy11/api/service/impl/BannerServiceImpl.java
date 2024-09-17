package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.api.service.BannerService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.model.BannerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


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
            banner=request.getBanner();
            banner.setCreator(coreService.getCurrentUser().getUsername());
            banner.setCreationTime(new Date());
            bannerRepository.save(banner);
        }
        banner.setLastModified(new Date());
        if (request.getRecordId()==null){
            banner.setRecordId(String.valueOf(banner.getId().getTimestamp()));
        }
        bannerRepository.save(banner);
        bannerDto.setBanner(banner);
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
