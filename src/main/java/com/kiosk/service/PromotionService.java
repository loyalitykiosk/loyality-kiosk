package com.kiosk.service;

import com.kiosk.domain.Promotion;
import com.kiosk.web.rest.dto.PromotionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Promotion.
 */
public interface PromotionService {

    /**
     * Save a promotion.
     *
     * @param promotionDTO the entity to save
     * @return the persisted entity
     */
    PromotionDTO save(PromotionDTO promotionDTO);

    /**
     *  Get all the promotions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Promotion> findAll(Pageable pageable);


    /**
     *  Get all the promotions of current user.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Promotion> findByUserIsCurrentUser(Pageable pageable);


    /**
     *  Get all the promotions of current user.
     *
     *  @return the list of entities
     */
    List<PromotionDTO> findByUserIsCurrentUser();

    /**
     *  Get the "id" promotion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PromotionDTO findOne(Long id);

    /**
     *  Delete the "id" promotion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
