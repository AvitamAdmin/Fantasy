package com.avitam.fantasy11.web.controllers.admin.settings;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.SEOForm;
import com.avitam.fantasy11.model.SEO;
import com.avitam.fantasy11.model.SEORepository;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/admin/seo")
public class SEOController {

    @Autowired
     private SEORepository seoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAll(Model model){
        List<SEO> datas=new ArrayList<>();
        List<SEO> seos = seoRepository.findAll();
        for(SEO seo:seos){
            if(seo.getId()!=null) {
                byte[] image = seo.getImage().getData();
                seo.setPic(Base64.getEncoder().encodeToString(image));
                datas.add(seo);
            }
        }
        model.addAttribute("models",datas);
        return "seo/seos";
    }

    @GetMapping("/edit")
        public String edit(@RequestParam("id")String id, Model model){

        Optional<SEO> seoOptional = seoRepository.findByRecordId(id);
        if (seoOptional.isPresent()) {
            SEO seo = seoOptional.get();

            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            SEOForm seoForm = modelMapper.map(seo, SEOForm.class);
            seoForm.setId(String.valueOf(seo.getId()));

            byte[] image = seo.getImage().getData();
            seo.setPic(Base64.getEncoder().encodeToString(image));
            seoForm.setPic(seo.getPic());

            model.addAttribute("editForm", seoForm);
        }
        return "seo/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") SEOForm seoForm, Model model, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "seo/edit";
        }
        seoForm.setLastModified(new Date());
        if (seoForm.getId() == null) {
            seoForm.setCreationTime(new Date());
            seoForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        SEO seo = modelMapper.map(seoForm, SEO.class);

        byte[] fig= seoForm.getImage().getBytes();
        Binary binary=new Binary(fig);

        Optional<SEO> seoOptional = seoRepository.findById(seoForm.getId());
        if(seoOptional.isPresent()){
            seo.setId(seoOptional.get().getId());
        }
        seo.setImage(binary);
        seoRepository.save(seo);

        if(seo.getRecordId()==null){
                        seo.setRecordId(String.valueOf(seo.getId().getTimestamp()));
        }
        seoRepository.save(seo);
        model.addAttribute("editForm", seoForm);
        return "redirect:/admin/seo";
    }

    @GetMapping("/add")
    public String add(Model model){
        SEOForm form = new SEOForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        return "seo/edit";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

            seoRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/seo";
    }

}
