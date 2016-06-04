package com.kiosk.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.kiosk.domain.Kiosk;
import com.kiosk.security.AuthoritiesConstants;
import com.kiosk.security.SecurityUtils;
import com.kiosk.service.KioskService;
import com.kiosk.web.rest.util.HeaderUtil;
import com.kiosk.web.rest.util.PaginationUtil;
import com.kiosk.web.rest.dto.KioskDTO;
import com.kiosk.web.rest.mapper.KioskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
 * REST controller for managing Kiosk.
 */
@RestController
@RequestMapping("/api")
public class KioskResource {

    private final Logger log = LoggerFactory.getLogger(KioskResource.class);

    @Inject
    private KioskService kioskService;

    @Inject
    private KioskMapper kioskMapper;

    /**
     * POST  /kiosks : Create a new kiosk.
     *
     * @param kioskDTO the kioskDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kioskDTO, or with status 400 (Bad Request) if the kiosk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kiosks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<KioskDTO> createKiosk(@Valid @RequestBody KioskDTO kioskDTO) throws URISyntaxException {
        log.debug("REST request to save Kiosk : {}", kioskDTO);
        if (kioskDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("kiosk", "idexists", "A new kiosk cannot already have an ID")).body(null);
        }
        KioskDTO result = kioskService.save(kioskDTO);
        return ResponseEntity.created(new URI("/api/kiosks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("kiosk", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kiosks : Updates an existing kiosk.
     *
     * @param kioskDTO the kioskDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kioskDTO,
     * or with status 400 (Bad Request) if the kioskDTO is not valid,
     * or with status 500 (Internal Server Error) if the kioskDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/kiosks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<KioskDTO> updateKiosk(@Valid @RequestBody KioskDTO kioskDTO) throws URISyntaxException {
        log.debug("REST request to update Kiosk : {}", kioskDTO);
        if (kioskDTO.getId() == null) {
            return createKiosk(kioskDTO);
        }
        KioskDTO result = kioskService.save(kioskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("kiosk", kioskDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kiosks : get all the kiosks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of kiosks in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/kiosks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<KioskDTO>> getAllKiosks(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Kiosks");
        Page<Kiosk> page;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            page = kioskService.findAll(pageable);
        }else{
            page = kioskService.findByUserIsCurrentUser(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/kiosks");
        return new ResponseEntity<>(kioskMapper.kiosksToKioskDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /kiosks/:id : get the "id" kiosk.
     *
     * @param id the id of the kioskDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kioskDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/kiosks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KioskDTO> getKiosk(@PathVariable Long id) {
        log.debug("REST request to get Kiosk : {}", id);
        KioskDTO kioskDTO = kioskService.findOne(id);
        return Optional.ofNullable(kioskDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kiosks/:id : delete the "id" kiosk.
     *
     * @param id the id of the kioskDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/kiosks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured(AuthoritiesConstants.ADMIN)
    @Timed
    public ResponseEntity<Void> deleteKiosk(@PathVariable Long id) {
        log.debug("REST request to delete Kiosk : {}", id);
        kioskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kiosk", id.toString())).build();
    }

}
