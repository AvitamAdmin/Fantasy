package com.avitam.fantasy11.web.controllers.admin.mobiletoken;

import com.avitam.fantasy11.api.dto.MobileTokenDto;
import com.avitam.fantasy11.api.dto.MobileTokenWsDto;
import com.avitam.fantasy11.api.service.MobileTokenService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.repository.MobileTokenRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin/mobileToken")
public class MobileTokenController extends BaseController {
    @Autowired
    private MobileTokenRepository mobileTokenRepository;
    @Autowired
    private MobileTokenService mobileTokenService;
    @Autowired
    private ModelMapper modelMapper;
    private static final String ADMIN_MOBILETOKEN="/admin/mobileToken";


    @PostMapping
    @ResponseBody
    public MobileTokenWsDto getAllMobileToken(@RequestBody MobileTokenWsDto mobileTokenWsDto) {
        Pageable pageable=getPageable(mobileTokenWsDto.getPage(),mobileTokenWsDto.getSizePerPage(),mobileTokenWsDto.getSortDirection(),mobileTokenWsDto.getSortField());
        MobileTokenDto mobileTokenDto = CollectionUtils.isNotEmpty(mobileTokenWsDto.getMobileTokenDtoList()) ? mobileTokenWsDto.getMobileTokenDtoList().get(0) : new MobileTokenDto();
        MobileToken mobileToken = modelMapper.map(mobileTokenDto,MobileToken.class);
        Page<MobileToken> page = isSearchActive(mobileToken) !=null ? mobileTokenRepository.findAll(Example.of(mobileToken),pageable) : mobileTokenRepository.findAll(pageable);
        mobileTokenWsDto.setMobileTokenDtoList(modelMapper.map(page.getContent(), List.class));
        mobileTokenWsDto.setBaseUrl(ADMIN_MOBILETOKEN);
        mobileTokenWsDto.setTotalPages(page.getTotalPages());
        mobileTokenWsDto.setTotalRecords(page.getTotalElements());
        return mobileTokenWsDto;

    }
    @GetMapping("/get")
    @ResponseBody
    public MobileTokenWsDto getActiveMobileToken() {
        MobileTokenWsDto mobileTokenWsDto = new MobileTokenWsDto();
        mobileTokenWsDto.setBaseUrl(ADMIN_MOBILETOKEN);
        mobileTokenWsDto.setMobileTokenDtoList(modelMapper.map(mobileTokenRepository.findByStatusOrderByIdentifier(true), List.class));
        return mobileTokenWsDto;
    }
    @PostMapping("/getedit")  @GetMapping("/edit")
    @ResponseBody
    public MobileTokenWsDto editMobileToken(@RequestBody MobileTokenWsDto request) {

        MobileTokenWsDto mobileTokenWsDto=new MobileTokenWsDto();
        mobileTokenWsDto.setBaseUrl(ADMIN_MOBILETOKEN);
        MobileToken mobileToken = mobileTokenRepository.findByRecordId(request.getMobileTokenDtoList().get(0).getRecordId());
        if(mobileToken !=null){
            mobileTokenWsDto.setMobileTokenDtoList(List.of(modelMapper.map(mobileToken,MobileTokenDto.class)));
        }
        return mobileTokenWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MobileTokenWsDto handleEdit(@RequestBody MobileTokenWsDto request) {
         return mobileTokenService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public MobileTokenWsDto addMobileToken() {
        MobileTokenWsDto mobileTokenWsDto = new MobileTokenWsDto();
        mobileTokenWsDto.setMobileTokenDtoList(modelMapper.map(mobileTokenRepository.findByStatusOrderByIdentifier(true), List.class));
        mobileTokenWsDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public MobileTokenWsDto deleteMobileToken(@RequestBody MobileTokenWsDto mobileTokenWsDto) {
        for(MobileTokenDto mobileTokenDto : mobileTokenWsDto.getMobileTokenDtoList()){
            mobileTokenRepository.deleteByRecordId(mobileTokenDto.getRecordId());
        }
        mobileTokenWsDto.setMessage("Data Deleted Successfully!!");
        mobileTokenWsDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenWsDto;

    }

}

