package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.AvoidRepository;
import com.restaurant.angikar.service.AvoidService;
import com.restaurant.angikar.service.dto.AvoidDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.Avoid}.
 */
@RestController
@RequestMapping("/api/avoids")
public class AvoidResource {

    private final Logger log = LoggerFactory.getLogger(AvoidResource.class);

    private static final String ENTITY_NAME = "avoid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvoidService avoidService;

    private final AvoidRepository avoidRepository;

    public AvoidResource(AvoidService avoidService, AvoidRepository avoidRepository) {
        this.avoidService = avoidService;
        this.avoidRepository = avoidRepository;
    }

    /**
     * {@code POST  /avoids} : Create a new avoid.
     *
     * @param avoidDTO the avoidDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avoidDTO, or with status {@code 400 (Bad Request)} if the avoid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AvoidDTO> createAvoid(@RequestBody AvoidDTO avoidDTO) throws URISyntaxException {
        log.debug("REST request to save Avoid : {}", avoidDTO);
        if (avoidDTO.getId() != null) {
            throw new BadRequestAlertException("A new avoid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AvoidDTO result = avoidService.save(avoidDTO);
        return ResponseEntity
            .created(new URI("/api/avoids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /avoids/:id} : Updates an existing avoid.
     *
     * @param id the id of the avoidDTO to save.
     * @param avoidDTO the avoidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avoidDTO,
     * or with status {@code 400 (Bad Request)} if the avoidDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avoidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvoidDTO> updateAvoid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvoidDTO avoidDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Avoid : {}, {}", id, avoidDTO);
        if (avoidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avoidDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avoidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AvoidDTO result = avoidService.update(avoidDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avoidDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /avoids/:id} : Partial updates given fields of an existing avoid, field will ignore if it is null
     *
     * @param id the id of the avoidDTO to save.
     * @param avoidDTO the avoidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avoidDTO,
     * or with status {@code 400 (Bad Request)} if the avoidDTO is not valid,
     * or with status {@code 404 (Not Found)} if the avoidDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the avoidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvoidDTO> partialUpdateAvoid(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvoidDTO avoidDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avoid partially : {}, {}", id, avoidDTO);
        if (avoidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avoidDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avoidRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvoidDTO> result = avoidService.partialUpdate(avoidDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avoidDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /avoids} : get all the avoids.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avoids in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AvoidDTO>> getAllAvoids(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Avoids");
        Page<AvoidDTO> page = avoidService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /avoids/:id} : get the "id" avoid.
     *
     * @param id the id of the avoidDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avoidDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvoidDTO> getAvoid(@PathVariable Long id) {
        log.debug("REST request to get Avoid : {}", id);
        Optional<AvoidDTO> avoidDTO = avoidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avoidDTO);
    }

    /**
     * {@code DELETE  /avoids/:id} : delete the "id" avoid.
     *
     * @param id the id of the avoidDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvoid(@PathVariable Long id) {
        log.debug("REST request to delete Avoid : {}", id);
        avoidService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
