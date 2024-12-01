package com.avitam.fantasy11.web.controllers.admin.kyc;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.service.KycService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.repository.KYCRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/kyc")
public class KYCController extends BaseController {

    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private KycService kycService;
    private static final String ADMIN_KYC="/admin/kyc";

    @PostMapping
    public KYCDto getAllKYCs(@RequestBody KYCDto kycDto) {
        Pageable pageable= getPageable(kycDto.getPage(),kycDto.getSizePerPage(),kycDto.getSortDirection(),kycDto.getSortField());
        KYC kyc=kycDto.getKyc();
        Page<KYC> page=isSearchActive(kyc)!=null ? kycRepository.findAll(Example.of(kyc),pageable):kycRepository.findAll(pageable);
        kycDto.setKycList(page.getContent());
        kycDto.setBaseUrl(ADMIN_KYC);
        kycDto.setTotalPages(page.getTotalPages());
        kycDto.setTotalRecords(page.getTotalElements());
        return kycDto;
    }
    @GetMapping("/get")
    @ResponseBody
    public KYCDto getActiveKYC(){
        KYCDto kycDto=new KYCDto();
        kycDto.setKycList(kycRepository.findByStatusOrderByIdentifier(true));
        kycDto.setBaseUrl(ADMIN_KYC);
        return kycDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public KYCDto editKyc(@RequestBody KYCDto request) {
        KYCDto kycDto=new KYCDto();
        KYC  kyc = kycRepository.findByRecordId(request.getRecordId());
        kycDto.setKyc(kyc);
        kycDto.setBaseUrl(ADMIN_KYC);
        return kycDto;
    }

    @PostMapping(value="/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public KYCDto handleEdit(@ModelAttribute KYCDto request) {
        return kycService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public KYCDto addKyc() {
        KYCDto kycDto = new KYCDto();
        kycDto.setKycList(kycRepository.findByStatusOrderByIdentifier(true));
        kycDto.setBaseUrl(ADMIN_KYC);
        return kycDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public KYCDto deleteKyc(@RequestBody KYCDto kycDto) {

        for (String id : kycDto.getRecordId().split(",")) {
           kycRepository.deleteByRecordId(id);
        }
        kycDto.setMessage("Data deleted Successfully");
        kycDto.setBaseUrl(ADMIN_KYC);
        return kycDto;
    }

}
