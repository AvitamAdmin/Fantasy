package com.avitam.fantasy11.web.controllers.admin.intface;

import com.avitam.fantasy11.api.dto.NodeDto;
import com.avitam.fantasy11.api.dto.NodeWsDto;
import com.avitam.fantasy11.api.dto.SearchDto;
import com.avitam.fantasy11.core.service.NodeService;
import com.avitam.fantasy11.core.service.UserService;
import com.avitam.fantasy11.model.Node;
import com.avitam.fantasy11.repository.NodeRepository;
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
@RequestMapping("/admin/interface")
public class InterfaceController extends BaseController {

    private static final String ADMIN_INTERFACE = "/admin/interface";
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private NodeService nodeService;

    @PostMapping
    @ResponseBody
    public NodeWsDto getAllNodes(@RequestBody NodeWsDto nodeWsDto) {
        Pageable pageable = getPageable(nodeWsDto.getPage(), nodeWsDto.getSizePerPage(), nodeWsDto.getSortDirection(), nodeWsDto.getSortField());
        NodeDto nodeDto = CollectionUtils.isNotEmpty(nodeWsDto.getNodeDtoList()) ? nodeWsDto.getNodeDtoList().get(0) : new NodeDto();
        Node node = modelMapper.map(nodeDto, Node.class);
        Page<Node> page = isSearchActive(node) != null ? nodeRepository.findAll(Example.of(node), pageable) : nodeRepository.findAll(pageable);
        nodeWsDto.setNodeDtoList(modelMapper.map(page.getContent(), List.class));
        nodeWsDto.setTotalPages(page.getTotalPages());
        nodeWsDto.setTotalRecords(page.getTotalElements());
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }

    @GetMapping("/getMenu")
    @ResponseBody
    public List<NodeDto> getMenu() {
        return userService.isAdminRole() ? nodeService.getAllNodes() : nodeService.getNodesForRoles();
    }

    @PostMapping("/getedit")
    @ResponseBody
    public NodeWsDto edit(@RequestBody NodeWsDto nodeWsDto) {
        Node node = nodeRepository.findByRecordId(nodeWsDto.getNodeDtoList().get(0).getRecordId());

        nodeWsDto.setNodeDtoList(List.of(modelMapper.map(node, NodeDto.class)));
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }


    @PostMapping("/edit")
    @ResponseBody
    public NodeWsDto handleEdit(@RequestBody NodeWsDto nodeWsDto) {
        return nodeService.handleEdit(nodeWsDto);
    }


    @GetMapping("/get")
    @ResponseBody
    public NodeWsDto getInterface() {
        NodeWsDto nodeWsDto = new NodeWsDto();
        nodeWsDto.setNodeDtoList(modelMapper.map(nodeRepository.findByParentNode(null), List.class));
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }

    @PostMapping("/delete")
    @ResponseBody
    public NodeWsDto delete(@RequestBody NodeWsDto nodeWsDto) {
        for (NodeDto nodeDto : nodeWsDto.getNodeDtoList()) {
            nodeRepository.deleteByRecordId(nodeDto.getRecordId());
        }
        nodeWsDto.setMessage("Data deleted successfully");
        nodeWsDto.setBaseUrl(ADMIN_INTERFACE);
        return nodeWsDto;
    }

    @GetMapping("/getAdvancedSearch")
    @ResponseBody
    public List<SearchDto> getSearchAttributes() {
        return getGroupedParentAndChildAttributes(new Node());
    }

}