package com.avitam.fantasy11.web.controllers.admin.advertise;

import com.avitam.fantasy11.api.dto.BannerDto;
import com.avitam.fantasy11.api.dto.BannerWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.api.service.BannerService;
import com.avitam.fantasy11.model.Banner;
import com.avitam.fantasy11.repository.BannerRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/banner")
public class AdvertiseController extends BaseController {
    private static final String ADMIN_BANNER = "/admin/banner";
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseBody
    public BannerWsDto getAllBanner(@RequestBody BannerWsDto bannerWsDto) {
        Pageable pageable = getPageable(bannerWsDto.getPage(), bannerWsDto.getSizePerPage(), bannerWsDto.getSortDirection(), bannerWsDto.getSortField());
        BannerDto bannerDto = CollectionUtils.isNotEmpty(bannerWsDto.getBannerDtoList()) ? bannerWsDto.getBannerDtoList().get(0) : null;
        Banner banner = bannerDto != null ? modelMapper.map(bannerDto, Banner.class) : null;
        Page<Banner> page = isSearchActive(banner) != null ? bannerRepository.findAll(Example.of(banner), pageable) : bannerRepository.findAll(pageable);
        bannerWsDto.setBannerDtoList(modelMapper.map(page.getContent(), List.class));
        bannerWsDto.setBaseUrl(ADMIN_BANNER);
        bannerWsDto.setTotalPages(page.getTotalPages());
        bannerWsDto.setTotalRecords(page.getTotalElements());
        return bannerWsDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public BannerWsDto getActiveBanner() {
        BannerWsDto bannerWsDto = new BannerWsDto();
        bannerWsDto.setBannerDtoList(modelMapper.map(bannerRepository.findByStatusOrderByIdentifier(true), List.class));
        bannerWsDto.setBaseUrl(ADMIN_BANNER);
        return bannerWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public BannerWsDto edit(@RequestBody BannerWsDto bannerWsDto) {
        Banner banner = bannerRepository.findByRecordId(bannerWsDto.getBannerDtoList().get(0).getRecordId());
        bannerWsDto.setBannerDtoList(List.of(modelMapper.map(banner, BannerDto.class)));
        bannerWsDto.setBaseUrl(ADMIN_BANNER);
        return bannerWsDto;
    }

    @PostMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BannerWsDto handleEdit(@ModelAttribute BannerWsDto bannerWsDto) {
        return bannerService.handleEdit(bannerWsDto);
    }

    @PostMapping("/delete")
    @ResponseBody
    public BannerWsDto delete(@RequestBody BannerWsDto bannerWsDto) {
        for (BannerDto bannerDto : bannerWsDto.getBannerDtoList()) {
            bannerRepository.deleteByRecordId(bannerDto.getRecordId());
        }
        bannerWsDto.setMessage("Data deleted successfully");
        bannerWsDto.setBaseUrl(ADMIN_BANNER);
        return bannerWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Banner());
    }
}
