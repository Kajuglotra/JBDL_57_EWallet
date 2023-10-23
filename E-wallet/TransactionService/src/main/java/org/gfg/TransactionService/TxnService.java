package org.gfg.TransactionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gfg.Utils.CommonConstants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TxnService  implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("txn-service", "txn-service");
        HttpEntity request = new HttpEntity(httpHeaders);

        JSONObject object = restTemplate.exchange("http://localhost:8080/user/getUser?contact=" +username, HttpMethod.GET
        , request, JSONObject.class).getBody();
        
        List<LinkedHashMap<String, String>> authorities =( List<LinkedHashMap<String, String>>)object.get("authorities");
        
        List<GrantedAuthority> requiredAuthority = authorities.stream().map(x-> x.get("authority")).map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
       
        User user = new User((String) object.get("username"), (String) object.get("password") , requiredAuthority);
        return user;
    }

    public String  initTxn(String username, String receiver, String amount, String purpose) throws JsonProcessingException {
        Txn txn = Txn.builder().txnId(UUID.randomUUID().toString()).
                amount(Double.valueOf(amount)).
                receiver(receiver).
                sender(username).
                purpose(purpose).txnStatus(TxnStatus.INITIATED).
                build();
        txnRepository.save(txn);
        // publish txn has been initiated

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", txn.getAmount());
        jsonObject.put("sender" , txn.getSender());
        jsonObject.put("receiver" , txn.getReceiver());
        jsonObject.put("txnid" , txn.getTxnId());
        kafkaTemplate.send(CommonConstants.TXN_INITIATED_TOPIC, objectMapper.writeValueAsString(jsonObject));

        return "txn is initiated ";
    }

    @KafkaListener(topics = CommonConstants.TXN_UPDATE_TOPIC, groupId ="transaction-group")
    public void updateTxn(String msg) throws JsonProcessingException {

        JSONObject jsonObject = objectMapper.readValue(msg,JSONObject.class);
        String txnId = (String) jsonObject.get("txnid");
        String status = (String) jsonObject.get("status");

        txnRepository.updateTxnStatus(txnId, TxnStatus.valueOf(status));

    }
}
