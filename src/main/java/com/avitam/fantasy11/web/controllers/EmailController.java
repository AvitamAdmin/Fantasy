package com.avitam.fantasy11.web.controllers;

import com.avitam.fantasy11.api.dto.EmailDto;
import com.avitam.fantasy11.mail.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/email")
public class EmailController extends BaseController{

    @Autowired
    private EmailSenderService senderMailService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public EmailDto sendEmails(@RequestBody EmailDto request){


        return senderMailService.sendEmail(request);

    }

}
