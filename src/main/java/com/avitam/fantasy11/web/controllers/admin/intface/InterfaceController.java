package com.avitam.fantasy11.web.controllers.admin.intface;

import com.avitam.fantasy11.core.model.Node;
import com.avitam.fantasy11.core.model.NodeRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.InterfaceForm;
import com.avitam.fantasy11.validation.InterfaceFormValidator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/interface")
public class InterfaceController {
    public static final int TOOL_KIT_NODE = 3;
    public static final int SCHEDULER_NODE = 2;
    public static final String TOOLKITS = "Toolkits";
    public static final String SCHEDULING = "Scheduling";

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(org.springframework.ui.Model model) {
        model.addAttribute("models", nodeRepository.findAll().stream().filter(node -> node.getParentNode() != null).collect(Collectors.toList()));
        return "interface/interfaces";
    }

    @GetMapping("/edit")
    public String editInterface(@RequestParam("id") Long id, Model model) {
        InterfaceForm interfaceForm = null;
        Optional<Node> interfaceOptional = nodeRepository.findById(id);
        if (interfaceOptional.isPresent()) {
            Node node = interfaceOptional.get();
            interfaceForm = modelMapper.map(node, InterfaceForm.class);
            model.addAttribute("editForm", interfaceForm);
        }
        model.addAttribute("nodes", nodeRepository.findAll().stream().filter(node -> node.getParentNode() == null).collect(Collectors.toList()));
        return "interface/edit";
    }

    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("editForm") InterfaceForm interfaceForm, Model model, BindingResult result) {
        new InterfaceFormValidator().validate(interfaceForm, result);
        if (result.hasErrors()) {
            model.addAttribute("message", result);
            return "interface/edit";
        }
        interfaceForm.setLastModified(new Date());
        if (interfaceForm.getId() == null) {
            interfaceForm.setCreationTime(new Date());
            interfaceForm.setCreator(coreService.getCurrentUser().getUsername());
        }
        Node node = modelMapper.map(interfaceForm, Node.class);
        if (StringUtils.isNotEmpty(interfaceForm.getParentNodeId())) {
            node.setParentNode(nodeRepository.getById(Long.valueOf(interfaceForm.getParentNodeId())));
        }
        if (node.getDisplayPriority() == null) {
            node.setDisplayPriority(1000);
        }
        nodeRepository.save(node);
        model.addAttribute("editForm", interfaceForm);
        return "redirect:/admin/interface";
    }

    @GetMapping("/add")
    public String addInterface(Model model) {
        InterfaceForm form = new InterfaceForm();
        form.setCreationTime(new Date());
        form.setLastModified(new Date());
        form.setStatus(true);
        form.setCreator(coreService.getCurrentUser().getUsername());
        model.addAttribute("editForm", form);
        model.addAttribute("nodes", nodeRepository.findAll().stream().filter(node -> node.getParentNode() == null).collect(Collectors.toList()));
        return "interface/edit";
    }

    @GetMapping("/delete")
    public String deleteInterface(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {
            nodeRepository.deleteById(Long.valueOf(id));
        }
        return "redirect:/admin/interface";
    }
}