package org.gfg.NotificationService.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.Utils.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserCreatedConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @KafkaListener(topics = CommonConstants.USER_CREATION_TOPIC, groupId = "notification-group")
    public void createWallet(String msg) throws JsonProcessingException {
        JSONObject jsonObject = objectMapper.readValue(msg, JSONObject.class);
        String contact = (String) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_CONTACT);
        Integer userId = (Integer) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_USER_ID);
        String email = (String) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_EMAIL);
        String name = (String) jsonObject.get(CommonConstants.USER_CREATION_TOPIC_NAME);

        simpleMailMessage.setTo(email);
        simpleMailMessage.setText("Welcome " + name +" to the platform !!" );
        simpleMailMessage.setSubject("Welcome to platform ! ");
        simpleMailMessage.setFrom("ewallet@gmail.com");

        javaMailSender.send(simpleMailMessage);


        System.out.println("+++We are here ");


    }
}
