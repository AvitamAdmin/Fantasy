package com.avitam.fantasy11.web.controllers.admin.address;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.AddressForm;
import com.avitam.fantasy11.model.*;
//import com.avitam.fantasy11.validation.AddressFormValidator;
import com.avitam.fantasy11.validation.AddressFormValidator;
import com.avitam.fantasy11.validation.InterfaceFormValidator;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/address")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", addressRepository.findAll());
        return "address/addresses";
    }

    @GetMapping("/edit")
    public String editAddress(@RequestParam("id") String id, Model model) {
        Optional<Address> addressOptional = addressRepository.findByRecordId(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            AddressForm addressForm = modelMapper.map(address, AddressForm.class);
            model.addAttribute("editForm", addressForm);
        }
        return "address/edit";
    }



    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") AddressForm addressForm, Model model, BindingResult result) {
        new AddressFormValidator().validate(addressForm, result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm",addressForm);
            return "address/edit";
        }
        addressForm.setLastModified(new Date());
        if (addressForm.getId() == null) {
            addressForm.setCreationTime(new Date());
            addressForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Address address = modelMapper.map(addressForm, Address.class);

        Optional<Address> addressOptional = addressRepository.findById(addressForm.getId());
        if(addressOptional.isPresent()){
            address.setId(addressOptional.get().getId());
        }

        addressRepository.save(address);
        if(address.getRecordId()==null)
        {
            address.setRecordId(String.valueOf(address.getId().getTimestamp()));
        }
        addressRepository.save(address);
        model.addAttribute("editForm", addressForm);
        return "redirect:/admin/address";
    }

    @GetMapping("/add")
    public String addAddress(Model model) {
        AddressForm form = new AddressForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        form.setMobileNumber(coreService.getCurrentUser().getMobileNumber());
        model.addAttribute("editForm", form);
        return "address/edit";
    }

    @GetMapping("/delete")
    public String deleteAddress(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
        addressRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/address";
    }
}
