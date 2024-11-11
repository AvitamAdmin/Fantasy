package com.avitam.fantasy11.mail;

import co.elastic.clients.elasticsearch.watcher.EmailBody;
import com.avitam.fantasy11.api.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    String fromEmailId="muthurethinam324@gmail.com";


    public EmailDto sendEmail(EmailDto request) {

        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromEmailId);
        message.setTo(request.getEmail().getTo());
        message.setText(request.getEmail().getContent());
        message.setSubject(request.getEmail().getSubject());

        javaMailSender.send(message);

        return request;
    }
}
