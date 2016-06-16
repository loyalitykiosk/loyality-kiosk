package com.kiosk.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kiosk.service.CardRequestService;
import com.kiosk.service.CardService;
import com.kiosk.web.rest.dto.CardDTO;
import com.kiosk.web.rest.util.HeaderUtil;
import com.kiosk.web.rest.dto.CardRequestDTO;
import com.kiosk.web.rest.mapper.CardRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CardRequest.
 */
@RestController
@RequestMapping("/api")
public class CardRequestResource {

    private final Logger log = LoggerFactory.getLogger(CardRequestResource.class);

    @Inject
    private CardRequestService cardRequestService;

    @Inject
    private CardService cardService;

    @Inject
    private CardRequestMapper cardRequestMapper;

    /**
     * POST  /card-requests : Create a new cardRequest.
     *
     * @param cardRequestDTO the cardRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cardRequestDTO, or with status 400 (Bad Request) if the cardRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/card-requests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> createCard(@Valid @RequestBody CardRequestDTO cardRequestDTO) throws URISyntaxException {
        log.debug("REST request to create card from cardRequest : {}", cardRequestDTO);
        if (cardRequestDTO.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cardRequest", "idexists", "Null card request")).body(null);
        }
        cardService.save(CardRequestDTO.createCardDTO(cardRequestDTO));
        cardRequestService.delete(cardRequestDTO.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * PUT  /card-requests : Updates an existing cardRequest.
     *
     * @param cardRequestDTO the cardRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cardRequestDTO,
     * or with status 400 (Bad Request) if the cardRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the cardRequestDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/card-requests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardRequestDTO> updateCardRequest(@Valid @RequestBody CardRequestDTO cardRequestDTO) throws URISyntaxException {
        log.debug("REST request to update CardRequest : {}", cardRequestDTO);
        if (cardRequestDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CardRequestDTO result = cardRequestService.save(cardRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cardRequest", cardRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /card-requests : get all the cardRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cardRequests in body
     */
    @RequestMapping(value = "/card-requests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CardRequestDTO> getAllCardRequests() {
        log.debug("REST request to get all CardRequests of current user");
        return cardRequestService.findRequestsFromCurrentUserKiosks();
    }

    /**
     * GET  /card-requests/:id : get the "id" cardRequest.
     *
     * @param id the id of the cardRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cardRequestDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/card-requests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CardRequestDTO> getCardRequest(@PathVariable Long id) {
        log.debug("REST request to get CardRequest : {}", id);
        CardRequestDTO cardRequestDTO = cardRequestService.findOne(id);
        return Optional.ofNullable(cardRequestDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /card-requests/:id : delete the "id" cardRequest.
     *
     * @param id the id of the cardRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/card-requests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCardRequest(@PathVariable Long id) {
        log.debug("REST request to delete CardRequest : {}", id);
        cardRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cardRequest", id.toString())).build();
    }

}
