package com.avitam.fantasy11.web.controllers.admin.kyc;

import com.avitam.fantasy11.api.dto.KYCDto;
import com.avitam.fantasy11.api.dto.KYCWsDto;
import com.avitam.fantasy11.api.dto.MatchScoreDto;
import com.avitam.fantasy11.api.service.KycService;
import com.avitam.fantasy11.model.KYC;
import com.avitam.fantasy11.repository.KYCRepository;
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


@RestController
@RequestMapping("/admin/kyc")
public class KYCController extends BaseController {

    @Autowired
    private KYCRepository kycRepository;
    @Autowired
    private KycService kycService;
    @Autowired
    ModelMapper modelMapper;
    private static final String ADMIN_KYC="/admin/kyc";

    @PostMapping
    public KYCWsDto getAllKYCs(@RequestBody KYCWsDto kycWsDto) {
        Pageable pageable= getPageable(kycWsDto.getPage(),kycWsDto.getSizePerPage(),kycWsDto.getSortDirection(),kycWsDto.getSortField());
        KYCDto kycDto = CollectionUtils.isNotEmpty(kycWsDto.getKycDtoList()) ? kycWsDto.getKycDtoList().get(0) : new KYCDto();
        KYC kyc = modelMapper.map(kycDto, KYC.class);
        Page<KYC> page=isSearchActive(kyc)!=null ? kycRepository.findAll(Example.of(kyc),pageable):kycRepository.findAll(pageable);
        kycWsDto.setKycDtoList(modelMapper.map(page.getContent(), List.class));
        kycWsDto.setBaseUrl(ADMIN_KYC);
        kycWsDto.setTotalPages(page.getTotalPages());
        kycWsDto.setTotalRecords(page.getTotalElements());
        return kycWsDto;
    }
    @GetMapping("/get")
    @ResponseBody
    public KYCWsDto getActiveKYC(){
        KYCWsDto kycWsDto=new KYCWsDto();
        kycWsDto.setKycDtoList(modelMapper.map(kycRepository.findByStatusOrderByIdentifier(true),List.class));
        kycWsDto.setBaseUrl(ADMIN_KYC);
        return kycWsDto;
    }
    @PostMapping("/getedit")
    @ResponseBody
    public KYCWsDto edit(@RequestBody KYCWsDto request) {
        KYCWsDto kycWsDto=new KYCWsDto();
        kycWsDto.setBaseUrl(ADMIN_KYC);
        KYC  kyc = kycRepository.findByRecordId(request.getRecordId());
        kycWsDto.setKycDtoList((List<KYCDto>) kyc);
        return kycWsDto;
    }

    @PostMapping(value="/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public KYCDto handleEdit(@ModelAttribute KYCDto request) {
        return kycService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public KYCWsDto addKyc() {
        KYCWsDto kycWsDto = new KYCWsDto();
        kycWsDto.setKycDtoList(modelMapper.map(kycRepository.findByStatusOrderByIdentifier(true),List.class));
        kycWsDto.setBaseUrl(ADMIN_KYC);
        return kycWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public KYCWsDto deleteKyc(@RequestBody KYCWsDto kycWsDto) {

        for (String id : kycWsDto.getRecordId().split(",")) {
           kycRepository.deleteByRecordId(id);
        }
        kycWsDto.setMessage("Data deleted Successfully");
        kycWsDto.setBaseUrl(ADMIN_KYC);
        return kycWsDto;
    }

}
