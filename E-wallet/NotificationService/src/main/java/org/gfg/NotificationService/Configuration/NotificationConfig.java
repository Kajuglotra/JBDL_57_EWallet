package org.gfg.NotificationService.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class NotificationConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return  new ObjectMapper();
    }

    @Bean
    public SimpleMailMessage getMailMessage(){
        return new SimpleMailMessage();
    }


}
