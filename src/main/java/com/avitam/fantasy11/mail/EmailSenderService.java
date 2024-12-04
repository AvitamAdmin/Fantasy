package com.avitam.fantasy11.mail;


import com.avitam.fantasy11.api.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    String fromEmailId="muthurethinam324@gmail.com";


    public EmailDto sendEmail(EmailDto request) {

        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(request.getTo());
        message.setText(request.getContent());
        message.setSubject(request.getSubject());

        javaMailSender.send(message);

        return request;
    }
}
