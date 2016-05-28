package com.kiosk.repository;

import com.kiosk.domain.Card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Card entity.
 */
public interface CardRepository extends JpaRepository<Card,Long> {

    @Query("select card from Card card where card.user.login = ?#{principal.username}")
    List<Card> findByUserIsCurrentUser();

    @Query("select card from Card card where (:number is null or card.number like :number||'%') and (:ownerName is null or card.ownerName like :ownerName||'%') and (:smsNumber is null or card.smsNumber like :smsNumber||'%') and card.user.login = ?#{principal.username}")
    Page<Card> findAllByNumberPhoneCustomerName(@Param("number") String number, @Param("ownerName")String ownerName, @Param("smsNumber")String smsNumber, Pageable pageable);

    Card findByNumber(String number);

}
