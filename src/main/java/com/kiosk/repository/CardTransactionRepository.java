package com.kiosk.repository;

import com.kiosk.domain.CardTransaction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CardTransaction entity.
 */
public interface CardTransactionRepository extends JpaRepository<CardTransaction,Long> {

}
