package com.kiosk.service.impl;

import com.kiosk.service.KioskService;
import com.kiosk.domain.Kiosk;
import com.kiosk.repository.KioskRepository;
import com.kiosk.web.rest.dto.KioskDTO;
import com.kiosk.web.rest.mapper.KioskMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

/**
 * Service Implementation for managing Kiosk.
 */
@Service
@Transactional
public class KioskServiceImpl implements KioskService{

    private final Logger log = LoggerFactory.getLogger(KioskServiceImpl.class);

    @Inject
    private KioskRepository kioskRepository;

    @Inject
    private KioskMapper kioskMapper;

    /**
     * Save a kiosk.
     *
     * @param kioskDTO the entity to save
     * @return the persisted entity
     */
    public KioskDTO save(KioskDTO kioskDTO) {
        log.debug("Request to save Kiosk : {}", kioskDTO);
        Kiosk kiosk = kioskMapper.kioskDTOToKiosk(kioskDTO);
        if (StringUtils.isEmpty(kiosk.getLicense())){
            kiosk.setLicense(RandomStringUtils.randomAlphabetic(20));
        }
        kiosk = kioskRepository.save(kiosk);
        KioskDTO result = kioskMapper.kioskToKioskDTO(kiosk);
        return result;
    }

    /**
     *  Get all the kiosks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Kiosk> findAll(Pageable pageable) {
        log.debug("Request to get all Kiosks");
        Page<Kiosk> result = kioskRepository.findAll(pageable);
        return result;
    }

    @Override
    public Page<Kiosk> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all Promotions for current user");
        Page<Kiosk> result = kioskRepository.findByCustomerIsCurrentUser(pageable);
        return result;
    }

    @Override
    public KioskDTO findByLicense(String license) {
        return kioskMapper.kioskToKioskDTO(kioskRepository.findByLicenseAndCurrentUser(license));
    }

    /**
     *  Get one kiosk by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public KioskDTO findOne(Long id) {
        log.debug("Request to get Kiosk : {}", id);
        Kiosk kiosk = kioskRepository.findOne(id);
        KioskDTO kioskDTO = kioskMapper.kioskToKioskDTO(kiosk);
        return kioskDTO;
    }

    @Override
    public KioskDTO findOneOfCurrentUser(Long id) {
        log.debug("Request to get Kiosk : {}", id);
        Kiosk kiosk = kioskRepository.findOneOfCurrentUser(id);
        KioskDTO kioskDTO = kioskMapper.kioskToKioskDTO(kiosk);
        return kioskDTO;
    }

    /**
     *  Delete the  kiosk by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Kiosk : {}", id);
        kioskRepository.delete(id);
    }
}
