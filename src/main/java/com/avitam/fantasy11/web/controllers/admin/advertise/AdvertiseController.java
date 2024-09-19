package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.api.service.BannerService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/banner")
public class AdvertiseController extends BaseController {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private BannerService bannerService;
    private static final String ADMIN_BANNER="/admin/banner";

    @PostMapping
    @ResponseBody
    public BannerDto getAllBanner(@RequestBody BannerDto bannerDto){
        Pageable pageable=getPageable(bannerDto.getPage(), bannerDto.getSizePerPage(), bannerDto.getSortDirection(), bannerDto.getSortField());
        Banner banner=bannerDto.getBanner();
        Page<Banner> page= isSearchActive(banner) !=null ? bannerRepository.findAll(Example.of(banner),pageable) : bannerRepository.findAll(pageable);
        bannerDto.setBannerList(page.getContent());
        bannerDto.setBaseUrl(ADMIN_BANNER);
        bannerDto.setTotalPages(page.getTotalPages());
        bannerDto.setTotalRecords(page.getTotalElements());
        return bannerDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public BannerDto getActiveBanner(){
        BannerDto bannerDto=new BannerDto();
        bannerDto.setBannerList(bannerRepository.findByStatusOrderByIdentifier(true));
        bannerDto.setBaseUrl(ADMIN_BANNER);
        return bannerDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public BannerDto edit(@RequestBody BannerDto request) {
        BannerDto bannerDto=new BannerDto();
        Banner banner= bannerRepository.findByRecordId(request.getRecordId());
        bannerDto.setBanner(banner);
        bannerDto.setBaseUrl(ADMIN_BANNER);
        return bannerDto;
    }

    @PostMapping("/edit")
    public BannerDto handleEdit(@RequestBody BannerDto request) {

        return bannerService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public BannerDto add() {
        BannerDto bannerDto=new BannerDto();
        bannerDto.setBannerList(bannerRepository.findByStatusOrderByIdentifier(true));
        bannerDto.setBaseUrl(ADMIN_BANNER);
        return bannerDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public BannerDto delete(@RequestBody BannerDto bannerDto) {
        for (String id : bannerDto.getRecordId().split(",")) {
            bannerRepository.deleteByRecordId(id);
        }
        bannerDto.setMessage("Data deleted successfully");
        bannerDto.setBaseUrl(ADMIN_BANNER);
        return bannerDto;
    }
}
