package com.avitam.fantasy11.api.service.impl;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.api.dto.BannerWsDto;
import com.avitam.fantasy11.api.service.BannerService;
import com.avitam.fantasy11.api.service.BaseService;
import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.repository.BannerRepository;
import com.avitam.fantasy11.repository.EntityConstants;

import java.util.ArrayList;
import java.util.List;

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
    public BannerWsDto handleEdit(BannerWsDto bannerWsDto) {

        Banner banner = null;
        List<Banner> banners = new ArrayList<>();
        List<BannerDto> bannerDtoList = bannerWsDto.getBannerDtoList();
        for (BannerDto bannerDto1 : bannerDtoList) {
            if (bannerDto1.getRecordId() != null) {
                banner = bannerRepository.findByRecordId(bannerDto1.getRecordId());
                modelMapper.map(bannerDto1, banner);
                bannerRepository.save(banner);
                bannerWsDto.setMessage("Data updated Successfully");
            } else {
                if (baseService.validateIdentifier(EntityConstants.BANNER, bannerDto1.getIdentifier()) != null) {
                    bannerWsDto.setSuccess(false);
                    bannerWsDto.setMessage("Identifier already present");
                    return bannerWsDto;
                }

                banner = modelMapper.map(bannerDto1, Banner.class);
            }
            if (bannerDto1.getImage() != null) {
                try {
                    banner.setImage(new Binary(bannerDto1.getImage().getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                    bannerWsDto.setMessage("Error processing image file");
                    return bannerWsDto;
                }
            }
            baseService.populateCommonData(banner);
            banner.setStatus(true);
            bannerRepository.save(banner);
            bannerWsDto.setMessage("Data added Successfully");
            if (banner.getRecordId() == null) {
                banner.setRecordId(String.valueOf(banner.getId().getTimestamp()));
            }
            bannerRepository.save(banner);
            banners.add(banner);
            bannerWsDto.setBaseUrl(ADMIN_BANNER);
        }
        bannerWsDto.setBannerDtoList(modelMapper.map(banner, List.class));
        return bannerWsDto;
    }

    @Override
    public void updateByRecordId(String recordId) {

        Banner banner = bannerRepository.findByRecordId(recordId);
        if (banner != null) {
            bannerRepository.save(banner);
        }

    }
}
