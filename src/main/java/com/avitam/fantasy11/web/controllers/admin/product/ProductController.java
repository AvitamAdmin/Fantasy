package com.avitam.fantasy11.web.controllers.admin.product;

import com.avitam.fantasy11.core.model.Product;
import com.avitam.fantasy11.core.model.ProductRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.ProductForm;
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
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;


    @GetMapping
    public String getAll(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("models", products);
        return "product/products";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductForm productForm = modelMapper.map(product, ProductForm.class);
            model.addAttribute("editForm", productForm);
        }

        return "product/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") ProductForm productForm, Model model, BindingResult result) {

        if (result.hasErrors()) {
            model.addAttribute("message", result);
            model.addAttribute("editForm", productForm);
            return "product/edit";
        }
        productForm.setLastModified(new Date());
        if (productForm.getId() == null) {
            productForm.setCreationTime(new Date());
            productForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        Product product = modelMapper.map(productForm, Product.class);
        productRepository.save(product);
        model.addAttribute("editForm", productForm);

        return "redirect:/admin/product";
    }

    @GetMapping("/add")
    public String add(Model model) {
        ProductForm form = new ProductForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());

        model.addAttribute("editForm", form);
        return "product/edit";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            productRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/product";
    }
}
