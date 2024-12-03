//package com.avitam.fantasy11.web.controllers.admin.address;
//
//import com.avitam.fantasy11.api.dto.AddressDto;
//import com.avitam.fantasy11.api.service.AddressService;
//import com.avitam.fantasy11.core.service.CoreService;
//import com.avitam.fantasy11.model.Address;
//import com.avitam.fantasy11.repository.AddressRepository;
//import com.avitam.fantasy11.web.controllers.BaseController;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/admin/address")
//public class AddressController extends BaseController {
//    @Autowired
//    private AddressRepository addressRepository;
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private CoreService coreService;
//
//    @Autowired
//    private AddressService addressService;
//
//    public static final String ADMIN_ADDRESS = "/admin/address";
//
//    @PostMapping
//    @ResponseBody
//    public AddressDto getAllAddress(@RequestBody AddressDto addressDto){
//
//        Pageable pageable=getPageable(addressDto.getPage(),addressDto.getSizePerPage(),addressDto.getSortDirection(),addressDto.getSortField());
//        Address address=addressDto.getAddress();
//        Page<Address> page=isSearchActive(address)!=null ? addressRepository.findAll(Example.of(address),pageable) : addressRepository.findAll(pageable);
//        addressDto.setAddressList(page.getContent());
//        addressDto.setTotalPages(page.getTotalPages());
//        addressDto.setTotalRecords(page.getTotalElements());
//        addressDto.setBaseUrl(ADMIN_ADDRESS);
//        return addressDto;
//    }
//
//    @GetMapping("/get")
//    @ResponseBody
//    public AddressDto getActiveAddressList() {
//        AddressDto addressDto = new AddressDto();
//        addressDto.setAddressList(addressRepository.findByStatusOrderByIdentifier(true));
//        addressDto.setBaseUrl(ADMIN_ADDRESS);
//        return addressDto;
//    }
//
//    @PostMapping("/getedit")
//    @ResponseBody
//    public AddressDto editAddress(@RequestBody AddressDto request) {
//        AddressDto addressDto = new AddressDto();
//        Address address = addressRepository.findByRecordId(request.getRecordId());
//        addressDto.setAddress(address);
//        addressDto.setBaseUrl(ADMIN_ADDRESS);
//        return addressDto;
//    }
//
//    @PostMapping("/edit")
//    @ResponseBody
//    public AddressDto handleEdit(@RequestBody AddressDto request) {
//
//        return addressService.handleEdit(request);
//    }
//
//    @GetMapping("/add")
//    @ResponseBody
//    public AddressDto addAddress() {
//        AddressDto addressDto = new AddressDto();
//        addressDto.setAddressList(addressRepository.findByStatusOrderByIdentifier(true));
//        addressDto.setBaseUrl(ADMIN_ADDRESS);
//        return addressDto;
//    }
//
//    @PostMapping("/delete")
//    @ResponseBody
//    public AddressDto deleteAddress(@RequestBody AddressDto addressDto) {
//        List<Address> addresses=addressDto.getAddressList();
//
//        for (String id : addressDto.getRecordId().split(",")) {
//            addressRepository.deleteByRecordId(id);
//        }
//        addressDto.setMessage("Data deleted successfully");
//        addressDto.setBaseUrl(ADMIN_ADDRESS);
//        return addressDto;
//    }
//}

package com.avitam.fantasy11.web.controllers.admin.address;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.dto.AddressWsDto;
import com.avitam.fantasy11.api.dto.UserWinningsDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.Address;
import com.avitam.fantasy11.model.UserWinnings;
import com.avitam.fantasy11.repository.AddressRepository;
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
@RequestMapping("/admin/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @Autowired
    private AddressService addressService;

    public static final String ADMIN_ADDRESS = "/admin/address";

    @PostMapping
    @ResponseBody
    public AddressDto getAllAddress(@RequestBody AddressWsDto addressWsDto){
        Pageable pageable=getPageable(addressWsDto.getPage(),addressWsDto.getSizePerPage(),addressWsDto.getSortDirection(),addressWsDto.getSortField());
        AddressDto addressDto = CollectionUtils.isNotEmpty(addressWsDto.getAddressDtoList()) ? addressWsDto.getAddressDtoList().get(0) : null;
        Address address = addressDto != null ? modelMapper.map(addressDto, Address.class) : null;
        Page<Address> page=isSearchActive(address)!=null ? addressRepository.findAll(Example.of(address),pageable) : addressRepository.findAll(pageable);
        addressWsDto.setAddressDtoList(modelMapper.map(page.getContent(),List.class));
        addressWsDto.setTotalPages(page.getTotalPages());
        addressWsDto.setTotalRecords(page.getTotalElements());
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public AddressWsDto getActiveAddressList() {
        AddressWsDto addressWsDto = new AddressWsDto();
        addressWsDto.setAddressDtoList(modelMapper.map(addressRepository.findByStatusOrderByIdentifier(true),List.class));
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public AddressWsDto editAddress(@RequestBody AddressWsDto addressWsDto) {
        AddressDto addressDto = new AddressDto();
       // addressWsDto.setAddressDtoList(modelMapper.map(addressRepository.findByRecordId(addressDto.getRecordId(),List.class)));
        Address address = addressRepository.findByRecordId(addressWsDto.getRecordId());
        addressWsDto.setAddressDtoList((List<AddressDto>) address);
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public AddressWsDto handleEdit(@RequestBody AddressWsDto addressWsDto) {
        return addressService.handleEdit(addressWsDto);
    }

    @GetMapping("/add")
    @ResponseBody
    public AddressWsDto addAddress() {
        AddressWsDto addressWsDto = new AddressWsDto();
        addressWsDto.setAddressDtoList(modelMapper.map(addressRepository.findByStatusOrderByIdentifier(true),List.class));
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public AddressWsDto deleteAddress(@RequestBody AddressWsDto addressWsDto) {
        for (AddressDto addressDto : addressWsDto.getAddressDtoList()) {
            addressRepository.deleteByRecordId(addressDto.getRecordId());
        }
        addressWsDto.setMessage("Data deleted successfully");
        addressWsDto.setBaseUrl(ADMIN_ADDRESS);
        return addressWsDto;
    }
}
