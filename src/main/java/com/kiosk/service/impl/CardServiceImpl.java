package com.kiosk.service.impl;

import com.kiosk.domain.CardTransaction;
import com.kiosk.domain.Kiosk;
import com.kiosk.domain.User;
import com.kiosk.repository.CardTransactionRepository;
import com.kiosk.repository.KioskRepository;
import com.kiosk.repository.UserRepository;
import com.kiosk.security.SecurityUtils;
import com.kiosk.service.CardService;
import com.kiosk.domain.Card;
import com.kiosk.repository.CardRepository;
import com.kiosk.service.SmsService;
import com.kiosk.service.UserService;
import com.kiosk.web.rest.dto.CardDTO;
import com.kiosk.web.rest.dto.CheckInDTO;
import com.kiosk.web.rest.mapper.CardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Duration;
import java.time.ZonedDateTime;

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
    private KioskRepository kioskRepository;

    @Inject
    private CardTransactionRepository cardTransactionRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private CardMapper cardMapper;

    @Inject
    private SmsService smsService;

    /**
     * Save a card.
     *
     * @param cardDTO the entity to save
     * @return the persisted entity
     */
    public CardDTO save(CardDTO cardDTO) {
        log.debug("Request to save Card : {}", cardDTO);
        Card card = cardMapper.cardDTOToCard(cardDTO);
        card.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        card = cardRepository.save(card);
        if (cardDTO.getId() == null){
            smsService.sendCardCreation(card);
        }
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

    @Override
    public Page<Card> findAllByNumberPhoneCustomerName(String number, String ownerName, String smsNumber, Pageable pageable) {
        log.debug("Request to get all Cards by Number Phone CustomerName");
        Page<Card> result = cardRepository.findAllByNumberPhoneCustomerName(number, ownerName, smsNumber, pageable);
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

    @Override
    public CardDTO findOneOfCurrentUser(Long id) {
        log.debug("Request to get Card : {}", id);
        Card card = cardRepository.findOneOfCurrentUser(id);
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

    @Override
    public CardDTO findByNumberOfCurrentUser(String number) {
        log.debug("Request to get Card by its number : {}", number);
        Card card = cardRepository.findByNumberOfCurrentUser(number);
        if (card != null){
            return  cardMapper.cardToCardDTO(card);
        }
        return  null;
    }

    @Override
    @Transactional
    public CardDTO  checkIn(CheckInDTO checkInDTO) {
        Card card = cardRepository.findByNumber(checkInDTO.getCardNumber());
        if (null == card) return null;
        Kiosk kiosk = kioskRepository.findByLicenseAndCurrentUser(checkInDTO.getKioskLicense());
        if (null == kiosk) return null;
        final User current = userRepository.currentUser();
        CardTransaction lastTransaction = cardTransactionRepository.findFirstByCardIdAndKioskIdOrderByTimestampDesc(card.getId(),kiosk.getId());
        if (lastTransaction != null && ZonedDateTime.now().minus(Duration.ofMinutes(current.getCheckinTimeout())).isBefore(lastTransaction.getTimestamp())){
            return  cardMapper.cardToCardDTO(card);
        }
        Long points = card.getType().getPoints(userService.getUserWithOutAuthorities());
        cardTransactionRepository.save(new CardTransaction(ZonedDateTime.now(),card.getId(),card.getBalance(),card.getBalance() + Math.abs(points),kiosk.getId()));
        card.setBalance(card.getBalance() + + Math.abs(points));
        card.setType(card.getType().upgrade(current, card));
        return  cardMapper.cardToCardDTO(cardRepository.save(card));
    }

}
