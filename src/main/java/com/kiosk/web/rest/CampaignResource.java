package com.kiosk.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kiosk.domain.Campaign;
import com.kiosk.domain.enumeration.CampaignStatus;
import com.kiosk.domain.enumeration.CampaignType;
import com.kiosk.repository.CampaignRepository;
import com.kiosk.repository.CardRepository;
import com.kiosk.repository.UserRepository;
import com.kiosk.security.SecurityUtils;
import com.kiosk.web.rest.util.HeaderUtil;
import com.kiosk.web.rest.util.PaginationUtil;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Campaign.
 */
@RestController
@RequestMapping("/api")
public class CampaignResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    @Inject
    private CampaignRepository campaignRepository;

    @Inject
    private CardRepository cardRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /campaigns : Create a new campaign.
     *
     * @param campaign the campaign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaign, or with status 400 (Bad Request) if the campaign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/campaigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) throws URISyntaxException {
        log.debug("REST request to save Campaign : {}", campaign);
        if (campaign.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("campaign", "idexists", "A new campaign cannot already have an ID")).body(null);
        }
        if (campaign.getType() == CampaignType.CUSTOM){
            campaign.setPromotion(null);
        }
        campaign.setStatus(CampaignStatus.NEW);
        campaign.setUser(userRepository.currentUser());
        Campaign result = campaignRepository.save(campaign);
        return ResponseEntity.created(new URI("/api/campaigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("campaign", result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /campaigns : Create a new campaign.
     *
     * @param campaign the campaign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new campaign, or with status 400 (Bad Request) if the campaign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/campaigns/count",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Pair<String, Integer> countCampaign(@RequestBody Campaign campaign) {
        log.debug("REST request to save Campaign : {}", campaign);
        Integer result = userRepository.currentUser().getUserSettings().getSmsBalance();
        result -= countSmsForCustomCampaign(campaign);
        return new Pair<>("smsNumber",result);
    }

    private Integer countSmsForCustomCampaign(Campaign campaign) {
        if (campaign.getCardType() == null || campaign.getCardType().isEmpty()){
            return 0;
        }
        return cardRepository.findByUserIsCurrentUserAndType(campaign.getCardType()).size();
    }

    /**
     * GET  /campaigns : get all the campaigns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of campaigns in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/campaigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Campaign>> getAllCampaigns(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Campaigns");
        Page<Campaign> page = campaignRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/campaigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /campaigns/:id : get the "id" campaign.
     *
     * @param id the id of the campaign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the campaign, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/campaigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Campaign> getCampaign(@PathVariable Long id) {
        log.debug("REST request to get Campaign : {}", id);
        Campaign campaign = campaignRepository.findByIdAndUserIsCurrentUser(id);
        return Optional.ofNullable(campaign)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /campaigns/:id : delete the "id" campaign.
     *
     * @param id the id of the campaign to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/campaigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        log.debug("REST request to delete Campaign : {}", id);
        if (campaignRepository.findByIdAndUserIsCurrentUser(id) != null){
            campaignRepository.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("campaign", id.toString())).build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
