package com.kiosk.service;

import com.kiosk.domain.Card;
import com.kiosk.web.rest.dto.CardDTO;
import com.kiosk.web.rest.dto.CheckInDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Card.
 */
public interface CardService {

    /**
     * Save a card.
     *
     * @param cardDTO the entity to save
     * @return the persisted entity
     */
    CardDTO save(CardDTO cardDTO);

    /**
     *  Get all the cards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Card> findAll(Pageable pageable);


    /**
     *  Get all the cards satisfy parameters.
     *
     *  @param pageable the pagination information
     *  @param number card number
     *  @param ownerName name of card owner
     *  @param smsNumber sms number
     *  @return the list of entities
     */
    Page<Card> findAllByNumberPhoneCustomerName(String number, String ownerName, String smsNumber, Pageable pageable);

    /**
     *  Get the "id" card.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CardDTO findOne(Long id);

    /**
     *  Get the "id" card.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CardDTO findOneOfCurrentUser(Long id);

    /**
     *  Delete the "id" card.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get the card by its number.
     *
     *  @param number the number of the entity
     *  @return the entity or null
     */
    CardDTO findByNumber(String number);

    /**
     *  Get the curent user's card by its number.
     *
     *  @param number the number of the entity
     *  @return the entity or null
     */
    CardDTO findByNumberOfCurrentUser(String number);

    /**
     *  Get the card by its number.
     *
     *  @param checkInDTO
     *  @return the entity or null
     */
    CardDTO checkIn(CheckInDTO checkInDTO);
}
