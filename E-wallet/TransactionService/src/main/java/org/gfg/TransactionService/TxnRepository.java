package org.gfg.TransactionService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    @Modifying
    @Transactional
    @Query("update Txn t set t.txnStatus=:status where t.txnId=:txnId")
    void updateTxnStatus(String txnId, TxnStatus status);
}
