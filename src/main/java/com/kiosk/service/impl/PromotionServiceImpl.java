package com.kiosk.service.impl;

import com.kiosk.repository.UserRepository;
import com.kiosk.security.SecurityUtils;
import com.kiosk.service.PromotionService;
import com.kiosk.domain.Promotion;
import com.kiosk.repository.PromotionRepository;
import com.kiosk.web.rest.dto.PromotionDTO;
import com.kiosk.web.rest.mapper.PromotionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Promotion.
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService{

    private final Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);

    @Inject
    private PromotionRepository promotionRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private PromotionMapper promotionMapper;

    /**
     * Save a promotion.
     *
     * @param promotionDTO the entity to save
     * @return the persisted entity
     */
    public PromotionDTO save(PromotionDTO promotionDTO) {
        log.debug("Request to save Promotion : {}", promotionDTO);
        Promotion promotion = promotionMapper.promotionDTOToPromotion(promotionDTO);
        promotion.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        promotion = promotionRepository.save(promotion);
        PromotionDTO result = promotionMapper.promotionToPromotionDTO(promotion);
        return result;
    }

    /**
     *  Get all the promotions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Promotion> findAll(Pageable pageable) {
        log.debug("Request to get all Promotions");
        Page<Promotion> result = promotionRepository.findAll(pageable);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Promotion> findByUserIsCurrentUser(Pageable pageable) {
        log.debug("Request to get all Promotions for current user");
        Page<Promotion> result = promotionRepository.findByUserIsCurrentUser(pageable);
        return result;
    }


    @Override
    public List<PromotionDTO> findByUserIsCurrentUser() {
        return promotionMapper.promotionsToPromotionDTOs(promotionRepository.findByUserIsCurrentUser());
    }

    /**
     *  Get one promotion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PromotionDTO findOne(Long id) {
        log.debug("Request to get Promotion : {}", id);
        Promotion promotion = promotionRepository.findOne(id);
        PromotionDTO promotionDTO = promotionMapper.promotionToPromotionDTO(promotion);
        return promotionDTO;
    }

    /**
     *  Delete the  promotion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Promotion : {}", id);
        promotionRepository.delete(id);
    }
}
