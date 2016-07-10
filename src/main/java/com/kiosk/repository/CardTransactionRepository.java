package com.kiosk.repository;

import com.kiosk.domain.CardTransaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * Spring Data JPA repository for the CardTransaction entity.
 */
public interface CardTransactionRepository extends JpaRepository<CardTransaction,Long> {

//    @Query("select transaction from CardTransaction transaction where transaction.kioskId=:kioskId and transaction.cardId=:cardId order by transaction.timestamp desc")
//    @QueryHints(@QueryHint(name = "JDBC_MAX_ROWS", value = "1"))
//    CardTransaction findLastTransaction(@Param("cardId")Long cardId, @Param("kioskId")Long kioskId);

    CardTransaction findFirstByCardIdAndKioskIdOrderByTimestampDesc(Long cardId, Long kioskId);
}
