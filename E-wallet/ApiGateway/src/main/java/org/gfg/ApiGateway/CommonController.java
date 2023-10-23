package org.gfg.ApiGateway;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
public class CommonController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/user/create")
    public JSONObject create(@RequestBody JSONObject jsonObject){
        return restTemplate.postForEntity("http://localhost:8080/user/create" , jsonObject, JSONObject.class).getBody();
    }
}
