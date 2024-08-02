package com.avitam.fantasy11.web.controllers.admin.sportApi;

import com.avitam.fantasy11.core.service.CoreService;
import com.avitam.fantasy11.model.SportsApiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings/sportsApi")
public class SportAPIController {

    @Autowired
    private SportsApiRepository sportsApiRepository;
    @Autowired
    private CoreService coreService;
    @Autowired
    private ModelMapper modelMapper;



}
