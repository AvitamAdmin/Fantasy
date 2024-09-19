package com.avitam.fantasy11.web.controllers.admin.intface;

import com.avitam.fantasy11.api.dto.NodeDto;
import com.avitam.fantasy11.core.service.NodeService;
import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.model.NodeRepository;
import com.avitam.fantasy11.web.controllers.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/interface")
public class InterfaceController extends BaseController {

    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private NodeService nodeService;
    private static final String ADMIN_INTERFACE="/admin/interface";

    @PostMapping
    @ResponseBody
    public NodeDto getAllNodes(@RequestBody NodeDto nodeDto){
        Pageable pageable=getPageable(nodeDto.getPage(),nodeDto.getSizePerPage(),nodeDto.getSortDirection(),nodeDto.getSortField());
        Node node=nodeDto.getNode();
        Page<Node> page= isSearchActive(node) != null ? nodeRepository.findAll(Example.of(node),pageable): nodeRepository.findAll(pageable);
        nodeDto.setNodeList(page.getContent());
        nodeDto.setTotalPages(page.getTotalPages());
        nodeDto.setTotalRecords(page.getTotalElements());
        nodeDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeDto;
    }
    @GetMapping("/get")
    @ResponseBody
    public NodeDto getActiveInterface() {
        NodeDto nodeDto=new NodeDto();
        nodeDto.setNodeList(nodeRepository.findByStatusOrderByIdentifier(true));
        nodeDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeDto;
    }

    @PostMapping("/getedit")
    @ResponseBody
    public NodeDto edit(@RequestBody NodeDto request) {
        NodeDto nodeDto=new NodeDto();
        Node node= nodeRepository.findByRecordId(request.getRecordId());
        nodeDto.setNode(node);
        nodeDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeDto;
    }

    @PostMapping("/edit")
    @ResponseBody
    public NodeDto handleEdit(@RequestBody NodeDto request) {

        return nodeService.handleEdit(request);
    }

    @GetMapping("/add")
    @ResponseBody
    public NodeDto addInterface() {
        NodeDto nodeDto=new NodeDto();
        nodeDto.setNodeList(nodeRepository.findByStatusOrderByIdentifier(true));
        nodeDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeDto;
    }

    @GetMapping("/delete")
    @ResponseBody
    public NodeDto delete(@RequestBody NodeDto nodeDto) {
        for (String id : nodeDto.getRecordId().split(",")) {

            nodeRepository.deleteByRecordId(id);
        }
        nodeDto.setMessage("Data deleted successfully");
        nodeDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeDto;
    }

}