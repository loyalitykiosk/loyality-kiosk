package com.kiosk.service.impl;

import com.kiosk.service.CardRequestService;
import com.kiosk.domain.CardRequest;
import com.kiosk.repository.CardRequestRepository;
import com.kiosk.web.rest.dto.CardRequestDTO;
import com.kiosk.web.rest.mapper.CardRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CardRequest.
 */
@Service
@Transactional
public class CardRequestServiceImpl implements CardRequestService{

    private final Logger log = LoggerFactory.getLogger(CardRequestServiceImpl.class);

    @Inject
    private CardRequestRepository cardRequestRepository;

    @Inject
    private CardRequestMapper cardRequestMapper;

    /**
     * Save a cardRequest.
     *
     * @param cardRequestDTO the entity to save
     * @return the persisted entity
     */
    public CardRequestDTO save(CardRequestDTO cardRequestDTO) {
        log.debug("Request to save CardRequest : {}", cardRequestDTO);
        CardRequest cardRequest = cardRequestMapper.cardRequestDTOToCardRequest(cardRequestDTO);
        cardRequest = cardRequestRepository.save(cardRequest);
        CardRequestDTO result = cardRequestMapper.cardRequestToCardRequestDTO(cardRequest);
        return result;
    }

    /**
     *  Get all the cardRequests.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CardRequestDTO> findAll() {
        log.debug("Request to get all CardRequests");
        List<CardRequestDTO> result = cardRequestRepository.findAll().stream()
            .map(cardRequestMapper::cardRequestToCardRequestDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }


    @Override
    public List<CardRequestDTO> findRequestsFromCurrentUserKiosks() {
        log.debug("Request to get all CardRequests created on kiosks of current user");
        List<CardRequestDTO> result = cardRequestRepository.findRequestsFromCurrentUserKiosks().stream()
            .map(cardRequestMapper::cardRequestToCardRequestDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one cardRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CardRequestDTO findOne(Long id) {
        log.debug("Request to get CardRequest : {}", id);
        CardRequest cardRequest = cardRequestRepository.findOne(id);
        CardRequestDTO cardRequestDTO = cardRequestMapper.cardRequestToCardRequestDTO(cardRequest);
        return cardRequestDTO;
    }

    /**
     *  Delete the  cardRequest by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CardRequest : {}", id);
        cardRequestRepository.delete(id);
    }
}
