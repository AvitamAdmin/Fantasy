package com.avitam.fantasy11.web.controllers.admin.mobiletoken;

import com.avitam.fantasy11.api.dto.MobileTokenDto;
import com.avitam.fantasy11.api.service.MobileTokenService;
import com.avitam.fantasy11.model.MobileToken;
import com.avitam.fantasy11.repository.MobileTokenRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/mobileToken")
public class MobileTokenController extends BaseController {
    @Autowired
    private MobileTokenRepository mobileTokenRepository;
    @Autowired
    private MobileTokenService mobileTokenService;
    private static final String ADMIN_MOBILETOKEN="/admin/mobileToken";


    @PostMapping
    @ResponseBody
    public MobileTokenDto getAllMobileToken(@RequestBody MobileTokenDto mobileTokenDto) {
        Pageable pageable=getPageable(mobileTokenDto.getPage(),mobileTokenDto.getSizePerPage(),mobileTokenDto.getSortDirection(),mobileTokenDto.getSortField());
        MobileToken mobileToken=mobileTokenDto.getMobileToken();
        Page<MobileToken> page=isSearchActive(mobileToken)!=null ? mobileTokenRepository.findAll(Example.of(mobileToken),pageable):mobileTokenRepository.findAll(pageable);
        mobileTokenDto.setMobileTokenList(page.getContent());
        mobileTokenDto.setBaseUrl(ADMIN_MOBILETOKEN);
        mobileTokenDto.setTotalPages(page.getTotalPages());
        mobileTokenDto.setTotalRecords(page.getTotalElements());
        return mobileTokenDto;
    }
    @GetMapping("/get")
    @ResponseBody
    public MobileTokenDto getActiveMobileToken() {
        MobileTokenDto mobileTokenDto = new MobileTokenDto();
        mobileTokenDto.setMobileTokenList(mobileTokenRepository.findByStatusOrderByIdentifier(true));
        mobileTokenDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenDto;
    }
    @PostMapping("/getedit")  @GetMapping("/edit")
    @ResponseBody
    public MobileTokenDto editMobileToken(@RequestBody MobileTokenDto request) {

        MobileTokenDto mobileTokenDto=new MobileTokenDto();
        MobileToken mobileToken=mobileTokenRepository.findByRecordId(request.getRecordId());
        mobileTokenDto.setMobileToken(mobileToken);
        mobileTokenDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public MobileTokenDto handleEdit(@RequestBody MobileTokenDto request) {
         return mobileTokenService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public MobileTokenDto addMobileToken() {
        MobileTokenDto mobileTokenDto = new MobileTokenDto();
        mobileTokenDto.setMobileTokenList(mobileTokenRepository.findByStatusOrderByIdentifier(true));
        mobileTokenDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public MobileTokenDto deleteMobileToken(@RequestBody MobileTokenDto mobileTokenDto) {
        for (String id : mobileTokenDto.getRecordId().split(",")) {
            mobileTokenRepository.deleteByRecordId(id);
        }
        mobileTokenDto.setMessage("Data deleted successfully");
        mobileTokenDto.setBaseUrl(ADMIN_MOBILETOKEN);
        return mobileTokenDto;
    }

}
