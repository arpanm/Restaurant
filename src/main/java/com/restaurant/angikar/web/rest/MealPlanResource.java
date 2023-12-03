package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.MealPlanRepository;
import com.restaurant.angikar.service.MealPlanService;
import com.restaurant.angikar.service.dto.MealPlanDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.MealPlan}.
 */
@RestController
@RequestMapping("/api/meal-plans")
public class MealPlanResource {

    private final Logger log = LoggerFactory.getLogger(MealPlanResource.class);

    private static final String ENTITY_NAME = "mealPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MealPlanService mealPlanService;

    private final MealPlanRepository mealPlanRepository;

    public MealPlanResource(MealPlanService mealPlanService, MealPlanRepository mealPlanRepository) {
        this.mealPlanService = mealPlanService;
        this.mealPlanRepository = mealPlanRepository;
    }

    /**
     * {@code POST  /meal-plans} : Create a new mealPlan.
     *
     * @param mealPlanDTO the mealPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mealPlanDTO, or with status {@code 400 (Bad Request)} if the mealPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MealPlanDTO> createMealPlan(@RequestBody MealPlanDTO mealPlanDTO) throws URISyntaxException {
        log.debug("REST request to save MealPlan : {}", mealPlanDTO);
        if (mealPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new mealPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MealPlanDTO result = mealPlanService.save(mealPlanDTO);
        return ResponseEntity
            .created(new URI("/api/meal-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meal-plans/:id} : Updates an existing mealPlan.
     *
     * @param id the id of the mealPlanDTO to save.
     * @param mealPlanDTO the mealPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MealPlanDTO> updateMealPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MealPlanDTO mealPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MealPlan : {}, {}", id, mealPlanDTO);
        if (mealPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MealPlanDTO result = mealPlanService.update(mealPlanDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mealPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meal-plans/:id} : Partial updates given fields of an existing mealPlan, field will ignore if it is null
     *
     * @param id the id of the mealPlanDTO to save.
     * @param mealPlanDTO the mealPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mealPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MealPlanDTO> partialUpdateMealPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MealPlanDTO mealPlanDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MealPlan partially : {}, {}", id, mealPlanDTO);
        if (mealPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MealPlanDTO> result = mealPlanService.partialUpdate(mealPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mealPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meal-plans} : get all the mealPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mealPlans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MealPlanDTO>> getAllMealPlans(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MealPlans");
        Page<MealPlanDTO> page = mealPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meal-plans/:id} : get the "id" mealPlan.
     *
     * @param id the id of the mealPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mealPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealPlanDTO> getMealPlan(@PathVariable Long id) {
        log.debug("REST request to get MealPlan : {}", id);
        Optional<MealPlanDTO> mealPlanDTO = mealPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mealPlanDTO);
    }

    /**
     * {@code DELETE  /meal-plans/:id} : delete the "id" mealPlan.
     *
     * @param id the id of the mealPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
        log.debug("REST request to delete MealPlan : {}", id);
        mealPlanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
