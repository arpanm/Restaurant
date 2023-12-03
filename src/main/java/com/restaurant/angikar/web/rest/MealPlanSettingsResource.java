package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.MealPlanSettingsRepository;
import com.restaurant.angikar.service.MealPlanSettingsService;
import com.restaurant.angikar.service.dto.MealPlanSettingsDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.MealPlanSettings}.
 */
@RestController
@RequestMapping("/api/meal-plan-settings")
public class MealPlanSettingsResource {

    private final Logger log = LoggerFactory.getLogger(MealPlanSettingsResource.class);

    private static final String ENTITY_NAME = "mealPlanSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MealPlanSettingsService mealPlanSettingsService;

    private final MealPlanSettingsRepository mealPlanSettingsRepository;

    public MealPlanSettingsResource(
        MealPlanSettingsService mealPlanSettingsService,
        MealPlanSettingsRepository mealPlanSettingsRepository
    ) {
        this.mealPlanSettingsService = mealPlanSettingsService;
        this.mealPlanSettingsRepository = mealPlanSettingsRepository;
    }

    /**
     * {@code POST  /meal-plan-settings} : Create a new mealPlanSettings.
     *
     * @param mealPlanSettingsDTO the mealPlanSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mealPlanSettingsDTO, or with status {@code 400 (Bad Request)} if the mealPlanSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MealPlanSettingsDTO> createMealPlanSettings(@RequestBody MealPlanSettingsDTO mealPlanSettingsDTO)
        throws URISyntaxException {
        log.debug("REST request to save MealPlanSettings : {}", mealPlanSettingsDTO);
        if (mealPlanSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new mealPlanSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MealPlanSettingsDTO result = mealPlanSettingsService.save(mealPlanSettingsDTO);
        return ResponseEntity
            .created(new URI("/api/meal-plan-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meal-plan-settings/:id} : Updates an existing mealPlanSettings.
     *
     * @param id the id of the mealPlanSettingsDTO to save.
     * @param mealPlanSettingsDTO the mealPlanSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MealPlanSettingsDTO> updateMealPlanSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MealPlanSettingsDTO mealPlanSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MealPlanSettings : {}, {}", id, mealPlanSettingsDTO);
        if (mealPlanSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MealPlanSettingsDTO result = mealPlanSettingsService.update(mealPlanSettingsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mealPlanSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meal-plan-settings/:id} : Partial updates given fields of an existing mealPlanSettings, field will ignore if it is null
     *
     * @param id the id of the mealPlanSettingsDTO to save.
     * @param mealPlanSettingsDTO the mealPlanSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanSettingsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mealPlanSettingsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MealPlanSettingsDTO> partialUpdateMealPlanSettings(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MealPlanSettingsDTO mealPlanSettingsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MealPlanSettings partially : {}, {}", id, mealPlanSettingsDTO);
        if (mealPlanSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanSettingsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanSettingsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MealPlanSettingsDTO> result = mealPlanSettingsService.partialUpdate(mealPlanSettingsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mealPlanSettingsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meal-plan-settings} : get all the mealPlanSettings.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mealPlanSettings in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MealPlanSettingsDTO>> getAllMealPlanSettings(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of MealPlanSettings");
        Page<MealPlanSettingsDTO> page;
        if (eagerload) {
            page = mealPlanSettingsService.findAllWithEagerRelationships(pageable);
        } else {
            page = mealPlanSettingsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meal-plan-settings/:id} : get the "id" mealPlanSettings.
     *
     * @param id the id of the mealPlanSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mealPlanSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealPlanSettingsDTO> getMealPlanSettings(@PathVariable Long id) {
        log.debug("REST request to get MealPlanSettings : {}", id);
        Optional<MealPlanSettingsDTO> mealPlanSettingsDTO = mealPlanSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mealPlanSettingsDTO);
    }

    /**
     * {@code DELETE  /meal-plan-settings/:id} : delete the "id" mealPlanSettings.
     *
     * @param id the id of the mealPlanSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlanSettings(@PathVariable Long id) {
        log.debug("REST request to delete MealPlanSettings : {}", id);
        mealPlanSettingsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
