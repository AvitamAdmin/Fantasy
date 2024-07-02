package com.avitam.billing.web.controllers.admin.billofsupply;

import com.avitam.billing.core.service.CoreService;
import com.avitam.billing.form.BillOfSupplyForm;
import com.avitam.billing.form.InterfaceForm;
import com.avitam.billing.model.*;
import com.avitam.billing.validation.BillOfSupplyValidator;
import com.avitam.billing.validation.InterfaceFormValidator;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/billofsupply")
public class BillOfSupplyController {

    @Autowired
    private BillOfSupplyRepository billOfSupplyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllBillOfSupplies(Model model) {
        model.addAttribute("models", billOfSupplyRepository.findAll());
        return "billofsupply/billofsupplies";
    }

    @GetMapping("/edit")
    public String editBillOfSupply(@RequestParam("id")ObjectId id, Model model) {
        BillOfSupplyForm billOfSupplyForm = null;
        Optional<BillOfSupply> billOfSupplyOptional = billOfSupplyRepository.findById(id);
        if (billOfSupplyOptional.isPresent()) {
            BillOfSupply billOfSupply = billOfSupplyOptional.get();
            billOfSupplyForm = modelMapper.map(billOfSupply, BillOfSupplyForm.class);
            billOfSupplyForm.setId(String.valueOf(billOfSupply.getId()));
            billOfSupplyForm.setCreator(coreService.getCurrentUser().getEmailId());
            List<Company> companies = companyRepository.findAll();
            for(Company company: companies) {
                billOfSupplyForm.setShopName(company.getName());
                billOfSupplyForm.setShopShortDescription(company.getShortDescription());
                billOfSupplyForm.setShopAddress(company.getAddress());
                billOfSupplyForm.setMobileNumber(company.getMobileNumber());
            }
            model.addAttribute("editForm", billOfSupplyForm);
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("products", productRepository.findAll());
        }
        return "billofsupply/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") BillOfSupplyForm billOfSupplyForm, Model model, BindingResult result) {
        new BillOfSupplyValidator().validate(billOfSupplyForm, result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "billofsupply/edit";
        }
        billOfSupplyForm.setLastModified(new Date());
        if (billOfSupplyForm.getId() == null) {
            billOfSupplyForm.setCreationTime(new Date());
            billOfSupplyForm.setCreator(coreService.getCurrentUser().getEmailId());
        }

        BillOfSupply billOfSupply = modelMapper.map(billOfSupplyForm, BillOfSupply.class);

        Optional<BillOfSupply> billOfSupplyOptional = billOfSupplyRepository.findById(billOfSupplyForm.getId());
        if(billOfSupplyOptional.isPresent()){
            billOfSupply.setId(billOfSupplyOptional.get().getId());
        }

        billOfSupplyRepository.save(billOfSupply);
        model.addAttribute("editForm", billOfSupplyForm);
        return "redirect:/admin/billofsupply";
    }

    @GetMapping("/add")
    public String addBillOfSupply(Model model) {
        BillOfSupplyForm form = new BillOfSupplyForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmailId());
        List<Company> companies = companyRepository.findAll();
        for(Company company: companies) {
            form.setShopName(company.getName());
            form.setShopShortDescription(company.getShortDescription());
            form.setShopAddress(company.getAddress());
            form.setMobileNumber(company.getMobileNumber());
        }
        List<BillOfSupply> billOfSupplyList = billOfSupplyRepository.findAll();
        int billNo = billOfSupplyList.size();
        form.setBillNo(++billNo);
        model.addAttribute("editForm", form);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "billofsupply/edit";
    }

    @GetMapping("/delete")
    public String deleteBillOfSupply(@RequestParam("id") ObjectId ids, Model model) {
       /* for (String id : ids.split(",")) {
            nodeRepository.deleteById(Integer.valueOf(id));
        }*/
        billOfSupplyRepository.deleteById(ids);
        return "redirect:/admin/billofsupply";
    }
}
