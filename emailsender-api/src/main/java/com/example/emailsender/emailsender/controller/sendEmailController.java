package com.example.emailsender.emailsender.controller;

import com.example.emailsender.emailsender.service.sendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@EnableAsync
@RestController
@RequestMapping("email")
public class sendEmailController {

    private final sendEmailService sendEmailService;

    @Autowired
    public sendEmailController(sendEmailService sendEmailService) {
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("sendAtt")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam("toAddress") String toAddress,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("attachment") MultipartFile attachment) {

        return sendEmailService.sendEmailWithAttachment(toAddress, subject, body, attachment);
    }

    @PostMapping("send")
    public ResponseEntity<String> sendEmail(
            @RequestParam("toAddress") String toAddress,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body) {

        return sendEmailService.sendEmail(toAddress, subject, body);
    }



    @PostMapping("add")
    public ResponseEntity<String> addEmail(
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam("toAddress") String toAddress,
            @RequestParam(value = "attachmentFilePath", required = false) String attachmentFilePath ){

        return sendEmailService.addEmail(subject, body, toAddress);
    }

    //fetch all data from db and scheduled emails

    @Scheduled(cron = "0 45 16 * * *")
    public void sendEmailToAllFromDB() {
        sendEmailService.sendEmailToAll();
    }

}
