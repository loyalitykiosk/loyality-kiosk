package com.kiosk.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kiosk.domain.CardTransaction;
import com.kiosk.service.CardTransactionService;
import com.kiosk.web.rest.util.HeaderUtil;
import com.kiosk.web.rest.dto.CardTransactionDTO;
import com.kiosk.web.rest.mapper.CardTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CardTransaction.
 */
@RestController
@RequestMapping("/api")
public class CardTransactionResource {

    private final Logger log = LoggerFactory.getLogger(CardTransactionResource.class);
        
    @Inject
    private CardTransactionService cardTransactionService;
    
    @Inject
    private CardTransactionMapper cardTransactionMapper;
    
    /**
     * POST  /card-transactions : Create a new cardTransaction.
     *
     * @param cardTransactionDTO the cardTransactionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cardTransactionDTO, or with status 400 (Bad Request) if the cardTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/card-transactions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardTransactionDTO> createCardTransaction(@Valid @RequestBody CardTransactionDTO cardTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save CardTransaction : {}", cardTransactionDTO);
        if (cardTransactionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cardTransaction", "idexists", "A new cardTransaction cannot already have an ID")).body(null);
        }
        CardTransactionDTO result = cardTransactionService.save(cardTransactionDTO);
        return ResponseEntity.created(new URI("/api/card-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cardTransaction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /card-transactions : Updates an existing cardTransaction.
     *
     * @param cardTransactionDTO the cardTransactionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cardTransactionDTO,
     * or with status 400 (Bad Request) if the cardTransactionDTO is not valid,
     * or with status 500 (Internal Server Error) if the cardTransactionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/card-transactions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardTransactionDTO> updateCardTransaction(@Valid @RequestBody CardTransactionDTO cardTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update CardTransaction : {}", cardTransactionDTO);
        if (cardTransactionDTO.getId() == null) {
            return createCardTransaction(cardTransactionDTO);
        }
        CardTransactionDTO result = cardTransactionService.save(cardTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cardTransaction", cardTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /card-transactions : get all the cardTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cardTransactions in body
     */
    @RequestMapping(value = "/card-transactions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CardTransactionDTO> getAllCardTransactions() {
        log.debug("REST request to get all CardTransactions");
        return cardTransactionService.findAll();
    }

    /**
     * GET  /card-transactions/:id : get the "id" cardTransaction.
     *
     * @param id the id of the cardTransactionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cardTransactionDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/card-transactions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardTransactionDTO> getCardTransaction(@PathVariable Long id) {
        log.debug("REST request to get CardTransaction : {}", id);
        CardTransactionDTO cardTransactionDTO = cardTransactionService.findOne(id);
        return Optional.ofNullable(cardTransactionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /card-transactions/:id : delete the "id" cardTransaction.
     *
     * @param id the id of the cardTransactionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/card-transactions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCardTransaction(@PathVariable Long id) {
        log.debug("REST request to delete CardTransaction : {}", id);
        cardTransactionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cardTransaction", id.toString())).build();
    }

}
