package com.kiosk.web.rest;

import com.kiosk.KioskApp;
import com.kiosk.domain.Card;
import com.kiosk.repository.CardRepository;
import com.kiosk.service.CardService;
import com.kiosk.web.rest.dto.CardDTO;
import com.kiosk.web.rest.mapper.CardMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kiosk.domain.enumeration.CardStatus;
import com.kiosk.domain.enumeration.CardType;

/**
 * Test class for the CardResource REST controller.
 *
 * @see CardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = KioskApp.class)
@WebAppConfiguration
@IntegrationTest
public class CardResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";
    private static final String DEFAULT_OWNER_NAME = "AAAAA";
    private static final String UPDATED_OWNER_NAME = "BBBBB";
    private static final String DEFAULT_OWNER_SURNAME = "AAAAA";
    private static final String UPDATED_OWNER_SURNAME = "BBBBB";
    private static final String DEFAULT_SMS_NUMBER = "AAAAA";
    private static final String UPDATED_SMS_NUMBER = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final CardStatus DEFAULT_STATUS = CardStatus.active;
    private static final CardStatus UPDATED_STATUS = CardStatus.blocked;

    private static final CardType DEFAULT_TYPE = CardType.BRONZE;
    private static final CardType UPDATED_TYPE = CardType.SILVER;

    @Inject
    private CardRepository cardRepository;

    @Inject
    private CardMapper cardMapper;

    @Inject
    private CardService cardService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCardMockMvc;

    private Card card;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CardResource cardResource = new CardResource();
        ReflectionTestUtils.setField(cardResource, "cardService", cardService);
        ReflectionTestUtils.setField(cardResource, "cardMapper", cardMapper);
        this.restCardMockMvc = MockMvcBuilders.standaloneSetup(cardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        card = new Card();
        card.setNumber(DEFAULT_NUMBER);
        card.setOwnerName(DEFAULT_OWNER_NAME);
        card.setOwnerSurname(DEFAULT_OWNER_SURNAME);
        card.setSmsNumber(DEFAULT_SMS_NUMBER);
        card.setEmail(DEFAULT_EMAIL);
        card.setStatus(DEFAULT_STATUS);
        card.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createCard() throws Exception {
        int databaseSizeBeforeCreate = cardRepository.findAll().size();

        // Create the Card
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isCreated());

        // Validate the Card in the database
        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeCreate + 1);
        Card testCard = cards.get(cards.size() - 1);
        assertThat(testCard.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCard.getOwnerName()).isEqualTo(DEFAULT_OWNER_NAME);
        assertThat(testCard.getOwnerSurname()).isEqualTo(DEFAULT_OWNER_SURNAME);
        assertThat(testCard.getSmsNumber()).isEqualTo(DEFAULT_SMS_NUMBER);
        assertThat(testCard.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCard.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCard.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setNumber(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isBadRequest());

        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setOwnerName(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isBadRequest());

        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOwnerSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setOwnerSurname(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isBadRequest());

        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmsNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setSmsNumber(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isBadRequest());

        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setStatus(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isBadRequest());

        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardRepository.findAll().size();
        // set the field null
        card.setType(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.cardToCardDTO(card);

        restCardMockMvc.perform(post("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isBadRequest());

        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCards() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get all the cards
        restCardMockMvc.perform(get("/api/cards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].ownerName").value(hasItem(DEFAULT_OWNER_NAME.toString())))
                .andExpect(jsonPath("$.[*].ownerSurname").value(hasItem(DEFAULT_OWNER_SURNAME.toString())))
                .andExpect(jsonPath("$.[*].smsNumber").value(hasItem(DEFAULT_SMS_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.ownerName").value(DEFAULT_OWNER_NAME.toString()))
            .andExpect(jsonPath("$.ownerSurname").value(DEFAULT_OWNER_SURNAME.toString()))
            .andExpect(jsonPath("$.smsNumber").value(DEFAULT_SMS_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get("/api/cards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        int databaseSizeBeforeUpdate = cardRepository.findAll().size();

        // Update the card
        Card updatedCard = new Card();
        updatedCard.setId(card.getId());
        updatedCard.setNumber(UPDATED_NUMBER);
        updatedCard.setOwnerName(UPDATED_OWNER_NAME);
        updatedCard.setOwnerSurname(UPDATED_OWNER_SURNAME);
        updatedCard.setSmsNumber(UPDATED_SMS_NUMBER);
        updatedCard.setEmail(UPDATED_EMAIL);
        updatedCard.setStatus(UPDATED_STATUS);
        updatedCard.setType(UPDATED_TYPE);
        CardDTO cardDTO = cardMapper.cardToCardDTO(updatedCard);

        restCardMockMvc.perform(put("/api/cards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cardDTO)))
                .andExpect(status().isOk());

        // Validate the Card in the database
        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeUpdate);
        Card testCard = cards.get(cards.size() - 1);
        assertThat(testCard.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCard.getOwnerName()).isEqualTo(UPDATED_OWNER_NAME);
        assertThat(testCard.getOwnerSurname()).isEqualTo(UPDATED_OWNER_SURNAME);
        assertThat(testCard.getSmsNumber()).isEqualTo(UPDATED_SMS_NUMBER);
        assertThat(testCard.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCard.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCard.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteCard() throws Exception {
        // Initialize the database
        cardRepository.saveAndFlush(card);
        int databaseSizeBeforeDelete = cardRepository.findAll().size();

        // Get the card
        restCardMockMvc.perform(delete("/api/cards/{id}", card.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Card> cards = cardRepository.findAll();
        assertThat(cards).hasSize(databaseSizeBeforeDelete - 1);
    }
}
