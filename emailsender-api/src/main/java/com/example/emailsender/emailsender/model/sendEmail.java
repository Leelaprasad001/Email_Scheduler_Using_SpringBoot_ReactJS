package com.example.emailsender.emailsender.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class sendEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String body;
    private String toAddress;

    @Transient
    private String attachmentFilePath;

}
