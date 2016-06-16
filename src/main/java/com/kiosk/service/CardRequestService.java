package com.kiosk.service;

import com.kiosk.domain.CardRequest;
import com.kiosk.web.rest.dto.CardRequestDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing CardRequest.
 */
public interface CardRequestService {

    /**
     * Save a cardRequest.
     *
     * @param cardRequestDTO the entity to save
     * @return the persisted entity
     */
    CardRequestDTO save(CardRequestDTO cardRequestDTO);

    /**
     *  Get all the cardRequests.
     *
     *  @return the list of entities
     */
    List<CardRequestDTO> findAll();

    /**
     *  Get all the cardRequests, created from kiosks of current user.
     *
     *  @return the list of entities
     */
    List<CardRequestDTO> findRequestsFromCurrentUserKiosks();

    /**
     *  Get the "id" cardRequest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CardRequestDTO findOne(Long id);

    /**
     *  Delete the "id" cardRequest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
