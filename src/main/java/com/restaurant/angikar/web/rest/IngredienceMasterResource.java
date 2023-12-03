package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.IngredienceMasterRepository;
import com.restaurant.angikar.service.IngredienceMasterService;
import com.restaurant.angikar.service.dto.IngredienceMasterDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.IngredienceMaster}.
 */
@RestController
@RequestMapping("/api/ingredience-masters")
public class IngredienceMasterResource {

    private final Logger log = LoggerFactory.getLogger(IngredienceMasterResource.class);

    private static final String ENTITY_NAME = "ingredienceMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngredienceMasterService ingredienceMasterService;

    private final IngredienceMasterRepository ingredienceMasterRepository;

    public IngredienceMasterResource(
        IngredienceMasterService ingredienceMasterService,
        IngredienceMasterRepository ingredienceMasterRepository
    ) {
        this.ingredienceMasterService = ingredienceMasterService;
        this.ingredienceMasterRepository = ingredienceMasterRepository;
    }

    /**
     * {@code POST  /ingredience-masters} : Create a new ingredienceMaster.
     *
     * @param ingredienceMasterDTO the ingredienceMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingredienceMasterDTO, or with status {@code 400 (Bad Request)} if the ingredienceMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IngredienceMasterDTO> createIngredienceMaster(@RequestBody IngredienceMasterDTO ingredienceMasterDTO)
        throws URISyntaxException {
        log.debug("REST request to save IngredienceMaster : {}", ingredienceMasterDTO);
        if (ingredienceMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new ingredienceMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IngredienceMasterDTO result = ingredienceMasterService.save(ingredienceMasterDTO);
        return ResponseEntity
            .created(new URI("/api/ingredience-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ingredience-masters/:id} : Updates an existing ingredienceMaster.
     *
     * @param id the id of the ingredienceMasterDTO to save.
     * @param ingredienceMasterDTO the ingredienceMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredienceMasterDTO,
     * or with status {@code 400 (Bad Request)} if the ingredienceMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingredienceMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IngredienceMasterDTO> updateIngredienceMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngredienceMasterDTO ingredienceMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IngredienceMaster : {}, {}", id, ingredienceMasterDTO);
        if (ingredienceMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredienceMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredienceMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IngredienceMasterDTO result = ingredienceMasterService.update(ingredienceMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingredienceMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ingredience-masters/:id} : Partial updates given fields of an existing ingredienceMaster, field will ignore if it is null
     *
     * @param id the id of the ingredienceMasterDTO to save.
     * @param ingredienceMasterDTO the ingredienceMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingredienceMasterDTO,
     * or with status {@code 400 (Bad Request)} if the ingredienceMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ingredienceMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ingredienceMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IngredienceMasterDTO> partialUpdateIngredienceMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngredienceMasterDTO ingredienceMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IngredienceMaster partially : {}, {}", id, ingredienceMasterDTO);
        if (ingredienceMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingredienceMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingredienceMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IngredienceMasterDTO> result = ingredienceMasterService.partialUpdate(ingredienceMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingredienceMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ingredience-masters} : get all the ingredienceMasters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingredienceMasters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<IngredienceMasterDTO>> getAllIngredienceMasters(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of IngredienceMasters");
        Page<IngredienceMasterDTO> page = ingredienceMasterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ingredience-masters/:id} : get the "id" ingredienceMaster.
     *
     * @param id the id of the ingredienceMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingredienceMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngredienceMasterDTO> getIngredienceMaster(@PathVariable Long id) {
        log.debug("REST request to get IngredienceMaster : {}", id);
        Optional<IngredienceMasterDTO> ingredienceMasterDTO = ingredienceMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingredienceMasterDTO);
    }

    /**
     * {@code DELETE  /ingredience-masters/:id} : delete the "id" ingredienceMaster.
     *
     * @param id the id of the ingredienceMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredienceMaster(@PathVariable Long id) {
        log.debug("REST request to delete IngredienceMaster : {}", id);
        ingredienceMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
