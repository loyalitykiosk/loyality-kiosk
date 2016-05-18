package com.kiosk.service.impl;

import com.kiosk.service.CardService;
import com.kiosk.domain.Card;
import com.kiosk.repository.CardRepository;
import com.kiosk.web.rest.dto.CardDTO;
import com.kiosk.web.rest.mapper.CardMapper;
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
 * Service Implementation for managing Card.
 */
@Service
@Transactional
public class CardServiceImpl implements CardService{

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    @Inject
    private CardRepository cardRepository;

    @Inject
    private CardMapper cardMapper;

    /**
     * Save a card.
     *
     * @param cardDTO the entity to save
     * @return the persisted entity
     */
    public CardDTO save(CardDTO cardDTO) {
        log.debug("Request to save Card : {}", cardDTO);
        Card card = cardMapper.cardDTOToCard(cardDTO);
        card = cardRepository.save(card);
        CardDTO result = cardMapper.cardToCardDTO(card);
        return result;
    }

    /**
     *  Get all the cards.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Card> findAll(Pageable pageable) {
        log.debug("Request to get all Cards");
        Page<Card> result = cardRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one card by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CardDTO findOne(Long id) {
        log.debug("Request to get Card : {}", id);
        Card card = cardRepository.findOne(id);
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);
        return cardDTO;
    }

    /**
     *  Delete the  card by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Card : {}", id);
        cardRepository.delete(id);
    }

    @Override
    public CardDTO findByNumber(String number) {
        log.debug("Request to get Card by its number : {}", number);
        Card card = cardRepository.findByNumber(number);
        if (card != null){
            return  cardMapper.cardToCardDTO(card);
        }
        return  null;
    }
}
