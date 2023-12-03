package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.QuantityRepository;
import com.restaurant.angikar.service.QuantityService;
import com.restaurant.angikar.service.dto.QuantityDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.restaurant.angikar.domain.Quantity}.
 */
@RestController
@RequestMapping("/api/quantities")
public class QuantityResource {

    private final Logger log = LoggerFactory.getLogger(QuantityResource.class);

    private static final String ENTITY_NAME = "quantity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuantityService quantityService;

    private final QuantityRepository quantityRepository;

    public QuantityResource(QuantityService quantityService, QuantityRepository quantityRepository) {
        this.quantityService = quantityService;
        this.quantityRepository = quantityRepository;
    }

    /**
     * {@code POST  /quantities} : Create a new quantity.
     *
     * @param quantityDTO the quantityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quantityDTO, or with status {@code 400 (Bad Request)} if the quantity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QuantityDTO> createQuantity(@RequestBody QuantityDTO quantityDTO) throws URISyntaxException {
        log.debug("REST request to save Quantity : {}", quantityDTO);
        if (quantityDTO.getId() != null) {
            throw new BadRequestAlertException("A new quantity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuantityDTO result = quantityService.save(quantityDTO);
        return ResponseEntity
            .created(new URI("/api/quantities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quantities/:id} : Updates an existing quantity.
     *
     * @param id the id of the quantityDTO to save.
     * @param quantityDTO the quantityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quantityDTO,
     * or with status {@code 400 (Bad Request)} if the quantityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quantityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuantityDTO> updateQuantity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuantityDTO quantityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Quantity : {}, {}", id, quantityDTO);
        if (quantityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quantityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quantityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuantityDTO result = quantityService.update(quantityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quantityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quantities/:id} : Partial updates given fields of an existing quantity, field will ignore if it is null
     *
     * @param id the id of the quantityDTO to save.
     * @param quantityDTO the quantityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quantityDTO,
     * or with status {@code 400 (Bad Request)} if the quantityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the quantityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the quantityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuantityDTO> partialUpdateQuantity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuantityDTO quantityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Quantity partially : {}, {}", id, quantityDTO);
        if (quantityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quantityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quantityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuantityDTO> result = quantityService.partialUpdate(quantityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quantityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /quantities} : get all the quantities.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quantities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<QuantityDTO>> getAllQuantities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("item-is-null".equals(filter)) {
            log.debug("REST request to get all Quantitys where item is null");
            return new ResponseEntity<>(quantityService.findAllWhereItemIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Quantities");
        Page<QuantityDTO> page = quantityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quantities/:id} : get the "id" quantity.
     *
     * @param id the id of the quantityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quantityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuantityDTO> getQuantity(@PathVariable Long id) {
        log.debug("REST request to get Quantity : {}", id);
        Optional<QuantityDTO> quantityDTO = quantityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quantityDTO);
    }

    /**
     * {@code DELETE  /quantities/:id} : delete the "id" quantity.
     *
     * @param id the id of the quantityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuantity(@PathVariable Long id) {
        log.debug("REST request to delete Quantity : {}", id);
        quantityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
