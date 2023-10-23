package org.gfg.UserService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class UserConfig {

    @Value("${kafka.bootstrap.server}")
    private String kafkaServer;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public Properties properties(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , kafkaServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return properties;
    }

    public ProducerFactory getProducerFactory(){
        return new DefaultKafkaProducerFactory(properties());
    }

    @Bean
    public KafkaTemplate<String,String> getKafkaTemplate(){
        return new KafkaTemplate<>(getProducerFactory());
    }
    @Bean
    public ObjectMapper objectMapper(){
        return  new ObjectMapper();
    }

}
