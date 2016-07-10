package com.kiosk.web.rest;

import com.kiosk.service.CardService;
import com.kiosk.service.CardTransactionService;
import com.kiosk.service.KioskService;
import com.kiosk.service.PromotionService;
import com.kiosk.web.rest.dto.CardDTO;
import com.kiosk.web.rest.dto.CheckInDTO;
import com.kiosk.web.rest.dto.KioskDTO;
import com.kiosk.web.rest.dto.PromotionDTO;
import com.kiosk.web.rest.mapper.CardTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing Public API.
 */
@RestController
@RequestMapping("/public-api")
public class PublicApiResource {

    private final Logger log = LoggerFactory.getLogger(PublicApiResource.class);

    @Inject
    private CardTransactionService cardTransactionService;

    @Inject
    private CardService cardService;

    @Inject
    private KioskService kioskService;

    @Inject
    private PromotionService promotionService;

    @Inject
    private CardTransactionMapper cardTransactionMapper;


    @RequestMapping(value = "/activate",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KioskDTO> activateKiosk(@RequestParam String kioskLicense) {
        KioskDTO result = kioskService.findByLicense(kioskLicense);
        if (null == result) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/checkin",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> checkIn(@RequestParam String kioskLicense,@RequestParam String cardNumber) {
        CardDTO result = cardService.checkIn(new CheckInDTO(kioskLicense, cardNumber));
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/card",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardDTO> getCard(@RequestParam String number) {
        CardDTO result = cardService.findByNumber(number);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/promotions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PromotionDTO>> getPromotions() {
        List<PromotionDTO> result = promotionService.findByUserIsCurrentUser();
        return ResponseEntity.ok(result);
    }

}
