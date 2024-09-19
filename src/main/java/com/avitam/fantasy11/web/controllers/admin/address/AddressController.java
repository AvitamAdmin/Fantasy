package com.avitam.fantasy11.web.controllers.admin.address;

import com.avitam.fantasy11.api.dto.AddressDto;
import com.avitam.fantasy11.api.service.AddressService;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.*;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
    public AddressDto getAllAddress(AddressDto addressDto){

        Pageable pageable=getPageable(addressDto.getPage(),addressDto.getSizePerPage(),addressDto.getSortDirection(),addressDto.getSortField());
        Address address=addressDto.getAddress();
        Page<Address> page=isSearchActive(address)!=null ? addressRepository.findAll(Example.of(address),pageable) : addressRepository.findAll(pageable);
        addressDto.setAddressList(page.getContent());
        addressDto.setTotalPages(page.getTotalPages());
        addressDto.setTotalRecords(page.getTotalElements());
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @GetMapping("/get")
    @ResponseBody
    public AddressDto getActiveAddressList() {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressList(addressRepository.findByStatusOrderByIdentifier(true));
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public AddressDto editAddress(@RequestBody AddressDto request) {
        AddressDto addressDto = new AddressDto();
        Address address = addressRepository.findByRecordId(request.getRecordId());
        addressDto.setAddress(address);
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public AddressDto handleEdit(@RequestBody AddressDto request) {

        return addressService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public AddressDto addAddress() {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressList(addressRepository.findByStatusOrderByIdentifier(true));
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public AddressDto deleteAddress(@RequestBody AddressDto addressDto) {
        for (String id : addressDto.getRecordId().split(",")) {
            addressRepository.deleteByRecordId(id);
        }
        addressDto.setMessage("Data deleted successfully");
        addressDto.setBaseUrl(ADMIN_ADDRESS);
        return addressDto;
    }
}
