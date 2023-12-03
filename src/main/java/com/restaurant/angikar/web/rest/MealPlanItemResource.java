package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.MealPlanItemRepository;
import com.restaurant.angikar.service.MealPlanItemService;
import com.restaurant.angikar.service.dto.MealPlanItemDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.MealPlanItem}.
 */
@RestController
@RequestMapping("/api/meal-plan-items")
public class MealPlanItemResource {

    private final Logger log = LoggerFactory.getLogger(MealPlanItemResource.class);

    private static final String ENTITY_NAME = "mealPlanItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MealPlanItemService mealPlanItemService;

    private final MealPlanItemRepository mealPlanItemRepository;

    public MealPlanItemResource(MealPlanItemService mealPlanItemService, MealPlanItemRepository mealPlanItemRepository) {
        this.mealPlanItemService = mealPlanItemService;
        this.mealPlanItemRepository = mealPlanItemRepository;
    }

    /**
     * {@code POST  /meal-plan-items} : Create a new mealPlanItem.
     *
     * @param mealPlanItemDTO the mealPlanItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mealPlanItemDTO, or with status {@code 400 (Bad Request)} if the mealPlanItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MealPlanItemDTO> createMealPlanItem(@RequestBody MealPlanItemDTO mealPlanItemDTO) throws URISyntaxException {
        log.debug("REST request to save MealPlanItem : {}", mealPlanItemDTO);
        if (mealPlanItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mealPlanItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MealPlanItemDTO result = mealPlanItemService.save(mealPlanItemDTO);
        return ResponseEntity
            .created(new URI("/api/meal-plan-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meal-plan-items/:id} : Updates an existing mealPlanItem.
     *
     * @param id the id of the mealPlanItemDTO to save.
     * @param mealPlanItemDTO the mealPlanItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanItemDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MealPlanItemDTO> updateMealPlanItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MealPlanItemDTO mealPlanItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MealPlanItem : {}, {}", id, mealPlanItemDTO);
        if (mealPlanItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MealPlanItemDTO result = mealPlanItemService.update(mealPlanItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mealPlanItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /meal-plan-items/:id} : Partial updates given fields of an existing mealPlanItem, field will ignore if it is null
     *
     * @param id the id of the mealPlanItemDTO to save.
     * @param mealPlanItemDTO the mealPlanItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mealPlanItemDTO,
     * or with status {@code 400 (Bad Request)} if the mealPlanItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mealPlanItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mealPlanItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MealPlanItemDTO> partialUpdateMealPlanItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MealPlanItemDTO mealPlanItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MealPlanItem partially : {}, {}", id, mealPlanItemDTO);
        if (mealPlanItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mealPlanItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mealPlanItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MealPlanItemDTO> result = mealPlanItemService.partialUpdate(mealPlanItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mealPlanItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /meal-plan-items} : get all the mealPlanItems.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mealPlanItems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MealPlanItemDTO>> getAllMealPlanItems(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of MealPlanItems");
        Page<MealPlanItemDTO> page;
        if (eagerload) {
            page = mealPlanItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = mealPlanItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meal-plan-items/:id} : get the "id" mealPlanItem.
     *
     * @param id the id of the mealPlanItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mealPlanItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealPlanItemDTO> getMealPlanItem(@PathVariable Long id) {
        log.debug("REST request to get MealPlanItem : {}", id);
        Optional<MealPlanItemDTO> mealPlanItemDTO = mealPlanItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mealPlanItemDTO);
    }

    /**
     * {@code DELETE  /meal-plan-items/:id} : delete the "id" mealPlanItem.
     *
     * @param id the id of the mealPlanItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlanItem(@PathVariable Long id) {
        log.debug("REST request to delete MealPlanItem : {}", id);
        mealPlanItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
