package com.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.constants.AppConstants;
import com.notification.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailRequestListener {
    @Autowired
    private JavaMailSender javaMailSender;


    @KafkaListener(topics = AppConstants.TOPIC, groupId = "group_customer_order")
    public void kafakSubscriberContent(String emailRequest) throws JsonProcessingException {
//        System.out.print("_____________ Msg fecthed From Kafka_________________");
//        System.out.println(emailRequest);

        // For Sending directly on email
        ObjectMapper mapper = new ObjectMapper();
        EmailRequest emailContent = mapper.readValue(emailRequest, EmailRequest.class);
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(emailContent.getTo());
        sm.setSubject(emailContent.getSubject());
        sm.setText(emailContent.getBody());

        javaMailSender.send(sm);
//        System.out.println(emailContent.getTo());
//        System.out.println(emailContent.getSubject());
//        System.out.println(emailContent.getBody());

    }



}
