package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.PincodeMasterRepository;
import com.restaurant.angikar.service.PincodeMasterService;
import com.restaurant.angikar.service.dto.PincodeMasterDTO;
import com.restaurant.angikar.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.restaurant.angikar.domain.PincodeMaster}.
 */
@RestController
@RequestMapping("/api/pincode-masters")
public class PincodeMasterResource {

    private final Logger log = LoggerFactory.getLogger(PincodeMasterResource.class);

    private static final String ENTITY_NAME = "pincodeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PincodeMasterService pincodeMasterService;

    private final PincodeMasterRepository pincodeMasterRepository;

    public PincodeMasterResource(PincodeMasterService pincodeMasterService, PincodeMasterRepository pincodeMasterRepository) {
        this.pincodeMasterService = pincodeMasterService;
        this.pincodeMasterRepository = pincodeMasterRepository;
    }

    /**
     * {@code POST  /pincode-masters} : Create a new pincodeMaster.
     *
     * @param pincodeMasterDTO the pincodeMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pincodeMasterDTO, or with status {@code 400 (Bad Request)} if the pincodeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PincodeMasterDTO> createPincodeMaster(@RequestBody PincodeMasterDTO pincodeMasterDTO) throws URISyntaxException {
        log.debug("REST request to save PincodeMaster : {}", pincodeMasterDTO);
        if (pincodeMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new pincodeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PincodeMasterDTO result = pincodeMasterService.save(pincodeMasterDTO);
        return ResponseEntity
            .created(new URI("/api/pincode-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pincode-masters/:id} : Updates an existing pincodeMaster.
     *
     * @param id the id of the pincodeMasterDTO to save.
     * @param pincodeMasterDTO the pincodeMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pincodeMasterDTO,
     * or with status {@code 400 (Bad Request)} if the pincodeMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pincodeMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PincodeMasterDTO> updatePincodeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PincodeMasterDTO pincodeMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PincodeMaster : {}, {}", id, pincodeMasterDTO);
        if (pincodeMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pincodeMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pincodeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PincodeMasterDTO result = pincodeMasterService.update(pincodeMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pincodeMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pincode-masters/:id} : Partial updates given fields of an existing pincodeMaster, field will ignore if it is null
     *
     * @param id the id of the pincodeMasterDTO to save.
     * @param pincodeMasterDTO the pincodeMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pincodeMasterDTO,
     * or with status {@code 400 (Bad Request)} if the pincodeMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pincodeMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pincodeMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PincodeMasterDTO> partialUpdatePincodeMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PincodeMasterDTO pincodeMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PincodeMaster partially : {}, {}", id, pincodeMasterDTO);
        if (pincodeMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pincodeMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pincodeMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PincodeMasterDTO> result = pincodeMasterService.partialUpdate(pincodeMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pincodeMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pincode-masters} : get all the pincodeMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pincodeMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PincodeMasterDTO>> getAllPincodeMasters(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PincodeMasters");
        Page<PincodeMasterDTO> page = pincodeMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pincode-masters/:id} : get the "id" pincodeMaster.
     *
     * @param id the id of the pincodeMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pincodeMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PincodeMasterDTO> getPincodeMaster(@PathVariable Long id) {
        log.debug("REST request to get PincodeMaster : {}", id);
        Optional<PincodeMasterDTO> pincodeMasterDTO = pincodeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pincodeMasterDTO);
    }

    /**
     * {@code DELETE  /pincode-masters/:id} : delete the "id" pincodeMaster.
     *
     * @param id the id of the pincodeMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePincodeMaster(@PathVariable Long id) {
        log.debug("REST request to delete PincodeMaster : {}", id);
        pincodeMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
