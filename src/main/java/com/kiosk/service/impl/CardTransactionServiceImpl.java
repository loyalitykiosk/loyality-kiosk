package com.kiosk.service.impl;

import com.kiosk.service.CardTransactionService;
import com.kiosk.domain.CardTransaction;
import com.kiosk.repository.CardTransactionRepository;
import com.kiosk.web.rest.dto.CardTransactionDTO;
import com.kiosk.web.rest.mapper.CardTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CardTransaction.
 */
@Service
@Transactional
public class CardTransactionServiceImpl implements CardTransactionService{

    private final Logger log = LoggerFactory.getLogger(CardTransactionServiceImpl.class);
    
    @Inject
    private CardTransactionRepository cardTransactionRepository;
    
    @Inject
    private CardTransactionMapper cardTransactionMapper;
    
    /**
     * Save a cardTransaction.
     * 
     * @param cardTransactionDTO the entity to save
     * @return the persisted entity
     */
    public CardTransactionDTO save(CardTransactionDTO cardTransactionDTO) {
        log.debug("Request to save CardTransaction : {}", cardTransactionDTO);
        CardTransaction cardTransaction = cardTransactionMapper.cardTransactionDTOToCardTransaction(cardTransactionDTO);
        cardTransaction = cardTransactionRepository.save(cardTransaction);
        CardTransactionDTO result = cardTransactionMapper.cardTransactionToCardTransactionDTO(cardTransaction);
        return result;
    }

    /**
     *  Get all the cardTransactions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CardTransactionDTO> findAll() {
        log.debug("Request to get all CardTransactions");
        List<CardTransactionDTO> result = cardTransactionRepository.findAll().stream()
            .map(cardTransactionMapper::cardTransactionToCardTransactionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }

    /**
     *  Get one cardTransaction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CardTransactionDTO findOne(Long id) {
        log.debug("Request to get CardTransaction : {}", id);
        CardTransaction cardTransaction = cardTransactionRepository.findOne(id);
        CardTransactionDTO cardTransactionDTO = cardTransactionMapper.cardTransactionToCardTransactionDTO(cardTransaction);
        return cardTransactionDTO;
    }

    /**
     *  Delete the  cardTransaction by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CardTransaction : {}", id);
        cardTransactionRepository.delete(id);
    }
}
