package com.avitam.fantasy11.web.controllers.admin.intface;

import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.NodeRepository;
import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.form.InterfaceForm;
import com.avitam.fantasy11.validation.InterfaceFormValidator;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/interface")
public class InterfaceController {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CoreService coreService;

    @GetMapping
    public String getAllModels(Model model) {
        model.addAttribute("models", nodeRepository.findAll().stream().filter(node -> node.getParentNodeId() != null).collect(Collectors.toList()));
        return "interface/interfaces";
    }



    @GetMapping("/edit")
    public String editInterface(@RequestParam("id") String id, Model model) {

        Optional<Node> interfaceOptional = nodeRepository.findByRecordId(id);
        if (interfaceOptional.isPresent()) {
            Node node = interfaceOptional.get();
            InterfaceForm interfaceForm = modelMapper.map(node, InterfaceForm.class);
            interfaceForm.setId(String.valueOf(node.getId()));
            model.addAttribute("editForm", interfaceForm);
        }
        model.addAttribute("nodes", nodeRepository.findAll().stream().filter(node -> node.getParentNodeId() == null).collect(Collectors.toList()));
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
            interfaceForm.setCreator(coreService.getCurrentUser().getEmail());
        }
        Node node = modelMapper.map(interfaceForm, Node.class);
        Optional<Node> nodeOptional=nodeRepository.findById(interfaceForm.getId());
        if(nodeOptional.isPresent()) {
            node.setId(nodeOptional.get().getId());
        }

        List<Node> nodeList=nodeRepository.findByParentNodeId(null);
        for(Node node1: nodeList){
         if(node1.getId().equals(interfaceForm.getParentNodeId())){
             node.setParentNodeId(String.valueOf(node1.getId()));
         }
        }

        if (node.getDisplayPriority() == null) {
            node.setDisplayPriority(1000);
        }
        nodeRepository.save(node);
        if(node.getRecordId()==null)
        {
            node.setRecordId(String.valueOf(node.getId().getTimestamp()));
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
        form.setCreator(coreService.getCurrentUser().getEmail());
        model.addAttribute("editForm", form);
        model.addAttribute("nodes", nodeRepository.findAll().stream().filter(node -> node.getParentNodeId() == null).collect(Collectors.toList()));
        return "interface/edit";
    }

    @GetMapping("/delete")
    public String deleteInterface(@RequestParam("id") String ids, Model model) {
        for (String id : ids.split(",")) {

            nodeRepository.deleteByRecordId(id);
        }
        return "redirect:/admin/interface";
    }

    @GetMapping("/edits")
    @ResponseBody
    public String editMultiple(@RequestParam("id") String ids, Model model) {
        List<InterfaceForm> interfaceForms = new ArrayList<>();
        InterfaceForm interfaceForm = null;
        for (String id : ids.split(",")) {
            Optional<Node> interfaceOptional = nodeRepository.findById(id);
            if (interfaceOptional.isPresent()) {
                Node node = interfaceOptional.get();
                interfaceForm = modelMapper.map(node, InterfaceForm.class);
            }
            interfaceForms.add(interfaceForm);
        }
        model.addAttribute("editForm", new InterfaceForm());
        model.addAttribute("dataToEdit", interfaceForms);
        model.addAttribute("nodes", nodeRepository.findAll().stream().filter(node -> node.getParentNode() == null).collect(Collectors.toList()));
        return "interface/edits";
    }

    @PostMapping("/edits")
    public String editMultiplePost(@ModelAttribute("editForm") InterfaceForm interfaceForm, Model model, BindingResult result) {
        List<InterfaceForm> interfaceFormList = interfaceForm.getInterfaceFormList();
        if (CollectionUtils.isNotEmpty(interfaceFormList)) {
            for (InterfaceForm interfaceForm1 : interfaceFormList) {
                Optional<Node> interfaceOptional = nodeRepository.findById(interfaceForm1.getId());
                if (interfaceOptional.isPresent()) {
                    Node node = interfaceOptional.get();
                    interfaceForm1.setStatus(node.getStatus());
                    interfaceForm1.setCreator(node.getCreator());
                    interfaceForm1.setCreationTime(node.getCreationTime());
                }
                interfaceForm.setLastModified(new Date());
                Node node = modelMapper.map(interfaceForm1, Node.class);
                nodeRepository.save(node);
            }
        }
        model.addAttribute("editForm", interfaceForm);
        return "redirect:/admin/interface";
    }

}