package com.kiosk.repository;

import com.kiosk.domain.Card;

import com.kiosk.domain.enumeration.CardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Card entity.
 */
public interface CardRepository extends JpaRepository<Card,Long> {

    @Query("select card from Card card where card.user.login = ?#{principal.username}")
    List<Card> findByUserIsCurrentUser();

    @Query("select card from Card card where card.user.login = ?#{principal.username} and card.type IN :type")
    List<Card> findByUserIsCurrentUserAndType(@Param("type") Collection<CardType> type);

    @Query("select card from Card card where card.user.id = :userId")
    List<Card> findByUser(@Param("userId") Long userId);

    @Query("select card from Card card where card.user.id = :userId and card.type IN :type")
    List<Card> findByUserAndType(@Param("userId") Long userId, @Param("type")Collection<CardType> cardType);

    @Query("select card from Card card where (:number is null or card.number like :number||'%') and (:ownerName is null or card.ownerName like :ownerName||'%') and (:smsNumber is null or card.smsNumber like :smsNumber||'%') and card.user.login = ?#{principal.username}")
    Page<Card> findAllByNumberPhoneCustomerName(@Param("number") String number, @Param("ownerName")String ownerName, @Param("smsNumber")String smsNumber, Pageable pageable);

    @Query("select card from Card card where card.number = :number and card.user.login = ?#{principal.username}")
    Card findByNumberOfCurrentUser(@Param("number") String number);

    @Query("select card from Card card where card.number = :number")
    Card findByNumber(@Param("number") String number);

    @Query("select card from Card card where card.id = :id and card.user.login = ?#{principal.username}")
    Card findOneOfCurrentUser(@Param("id")Long id);

    @Query("delete from Card card where card.user.id = :userId")
    @Modifying
    void deleteUserCards(@Param("userId") Long userId);
}
