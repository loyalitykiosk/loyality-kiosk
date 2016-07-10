package com.kiosk.service;

import com.kiosk.domain.Kiosk;
import com.kiosk.web.rest.dto.KioskDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Kiosk.
 */
public interface KioskService {

    /**
     * Save a kiosk.
     *
     * @param kioskDTO the entity to save
     * @return the persisted entity
     */
    KioskDTO save(KioskDTO kioskDTO);

    /**
     *  Get all the kiosks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Kiosk> findAll(Pageable pageable);

    /**
     *  Get all the kiosks of current user.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Kiosk> findByUserIsCurrentUser(Pageable pageable);


    KioskDTO findByLicense(String license);

    /**
     *  Get the "id" kiosk.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    KioskDTO findOne(Long id);

    /**
     *  Delete the "id" kiosk.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
