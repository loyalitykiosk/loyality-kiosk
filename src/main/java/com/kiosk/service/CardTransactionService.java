package com.kiosk.service;

import com.kiosk.domain.CardTransaction;
import com.kiosk.web.rest.dto.CardTransactionDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing CardTransaction.
 */
public interface CardTransactionService {

    /**
     * Save a cardTransaction.
     * 
     * @param cardTransactionDTO the entity to save
     * @return the persisted entity
     */
    CardTransactionDTO save(CardTransactionDTO cardTransactionDTO);

    /**
     *  Get all the cardTransactions.
     *  
     *  @return the list of entities
     */
    List<CardTransactionDTO> findAll();

    /**
     *  Get the "id" cardTransaction.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    CardTransactionDTO findOne(Long id);

    /**
     *  Delete the "id" cardTransaction.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
