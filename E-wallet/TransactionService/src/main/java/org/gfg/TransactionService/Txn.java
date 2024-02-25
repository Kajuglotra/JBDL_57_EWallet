package org.gfg.TransactionService;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Getter
public class Txn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private String txnId;
    private String sender;
    private String receiver;
    private Double amount;
   private String purpose;
   private TxnStatus txnStatus;
   
}
