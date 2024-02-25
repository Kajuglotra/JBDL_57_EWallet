package org.gfg.TransactionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/txn")
public class TxnController {

    @Autowired
    private TxnService txnService;

    @PostMapping("/initTxn")
    public String initTransaction(@RequestParam("receiver") String receiver,
                                @RequestParam("purpose") String purpose,
                                @RequestParam("amount") String amount) throws JsonProcessingException {

        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return txnService.initTxn(userDetails.getUsername(), receiver, amount, purpose);
    }

    @GetMapping("/getTxns")
    public List<Txn> getTxns(@RequestParam("page") int page,
                             @RequestParam("size") int size
                             ) throws JsonProcessingException {

        UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return txnService.getTxns(userDetails.getUsername(), page, size);
    }
}
