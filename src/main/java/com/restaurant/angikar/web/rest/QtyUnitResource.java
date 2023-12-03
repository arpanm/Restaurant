package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.QtyUnitRepository;
import com.restaurant.angikar.service.QtyUnitService;
import com.restaurant.angikar.service.dto.QtyUnitDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.QtyUnit}.
 */
@RestController
@RequestMapping("/api/qty-units")
public class QtyUnitResource {

    private final Logger log = LoggerFactory.getLogger(QtyUnitResource.class);

    private static final String ENTITY_NAME = "qtyUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QtyUnitService qtyUnitService;

    private final QtyUnitRepository qtyUnitRepository;

    public QtyUnitResource(QtyUnitService qtyUnitService, QtyUnitRepository qtyUnitRepository) {
        this.qtyUnitService = qtyUnitService;
        this.qtyUnitRepository = qtyUnitRepository;
    }

    /**
     * {@code POST  /qty-units} : Create a new qtyUnit.
     *
     * @param qtyUnitDTO the qtyUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qtyUnitDTO, or with status {@code 400 (Bad Request)} if the qtyUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<QtyUnitDTO> createQtyUnit(@RequestBody QtyUnitDTO qtyUnitDTO) throws URISyntaxException {
        log.debug("REST request to save QtyUnit : {}", qtyUnitDTO);
        if (qtyUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new qtyUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QtyUnitDTO result = qtyUnitService.save(qtyUnitDTO);
        return ResponseEntity
            .created(new URI("/api/qty-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qty-units/:id} : Updates an existing qtyUnit.
     *
     * @param id the id of the qtyUnitDTO to save.
     * @param qtyUnitDTO the qtyUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qtyUnitDTO,
     * or with status {@code 400 (Bad Request)} if the qtyUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qtyUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QtyUnitDTO> updateQtyUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QtyUnitDTO qtyUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QtyUnit : {}, {}", id, qtyUnitDTO);
        if (qtyUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qtyUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qtyUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QtyUnitDTO result = qtyUnitService.update(qtyUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qtyUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /qty-units/:id} : Partial updates given fields of an existing qtyUnit, field will ignore if it is null
     *
     * @param id the id of the qtyUnitDTO to save.
     * @param qtyUnitDTO the qtyUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qtyUnitDTO,
     * or with status {@code 400 (Bad Request)} if the qtyUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the qtyUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the qtyUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QtyUnitDTO> partialUpdateQtyUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QtyUnitDTO qtyUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QtyUnit partially : {}, {}", id, qtyUnitDTO);
        if (qtyUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, qtyUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!qtyUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QtyUnitDTO> result = qtyUnitService.partialUpdate(qtyUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qtyUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /qty-units} : get all the qtyUnits.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qtyUnits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<QtyUnitDTO>> getAllQtyUnits(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("quantity-is-null".equals(filter)) {
            log.debug("REST request to get all QtyUnits where quantity is null");
            return new ResponseEntity<>(qtyUnitService.findAllWhereQuantityIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of QtyUnits");
        Page<QtyUnitDTO> page = qtyUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /qty-units/:id} : get the "id" qtyUnit.
     *
     * @param id the id of the qtyUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qtyUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QtyUnitDTO> getQtyUnit(@PathVariable Long id) {
        log.debug("REST request to get QtyUnit : {}", id);
        Optional<QtyUnitDTO> qtyUnitDTO = qtyUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qtyUnitDTO);
    }

    /**
     * {@code DELETE  /qty-units/:id} : delete the "id" qtyUnit.
     *
     * @param id the id of the qtyUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQtyUnit(@PathVariable Long id) {
        log.debug("REST request to delete QtyUnit : {}", id);
        qtyUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
