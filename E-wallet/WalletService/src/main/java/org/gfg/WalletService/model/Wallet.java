package org.gfg.WalletService.model;

import lombok.*;
import org.gfg.Utils.UserIdentifier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String contact;
    @Enumerated(EnumType.STRING)
    private UserIdentifier userIdentifier;

    private String userIdentifierValue;

    private Double balance;
    @UpdateTimestamp
    private Date updatedAt;
    @CreationTimestamp
    private Date createdAt;

}
