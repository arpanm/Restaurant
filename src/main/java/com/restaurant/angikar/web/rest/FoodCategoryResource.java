package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.FoodCategoryRepository;
import com.restaurant.angikar.service.FoodCategoryService;
import com.restaurant.angikar.service.dto.FoodCategoryDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.FoodCategory}.
 */
@RestController
@RequestMapping("/api/food-categories")
public class FoodCategoryResource {

    private final Logger log = LoggerFactory.getLogger(FoodCategoryResource.class);

    private static final String ENTITY_NAME = "foodCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodCategoryService foodCategoryService;

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryResource(FoodCategoryService foodCategoryService, FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryService = foodCategoryService;
        this.foodCategoryRepository = foodCategoryRepository;
    }

    /**
     * {@code POST  /food-categories} : Create a new foodCategory.
     *
     * @param foodCategoryDTO the foodCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodCategoryDTO, or with status {@code 400 (Bad Request)} if the foodCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FoodCategoryDTO> createFoodCategory(@RequestBody FoodCategoryDTO foodCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save FoodCategory : {}", foodCategoryDTO);
        if (foodCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new foodCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodCategoryDTO result = foodCategoryService.save(foodCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/food-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-categories/:id} : Updates an existing foodCategory.
     *
     * @param id the id of the foodCategoryDTO to save.
     * @param foodCategoryDTO the foodCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the foodCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FoodCategoryDTO> updateFoodCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoodCategoryDTO foodCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FoodCategory : {}, {}", id, foodCategoryDTO);
        if (foodCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoodCategoryDTO result = foodCategoryService.update(foodCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /food-categories/:id} : Partial updates given fields of an existing foodCategory, field will ignore if it is null
     *
     * @param id the id of the foodCategoryDTO to save.
     * @param foodCategoryDTO the foodCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the foodCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the foodCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the foodCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FoodCategoryDTO> partialUpdateFoodCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoodCategoryDTO foodCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FoodCategory partially : {}, {}", id, foodCategoryDTO);
        if (foodCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoodCategoryDTO> result = foodCategoryService.partialUpdate(foodCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /food-categories} : get all the foodCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FoodCategoryDTO>> getAllFoodCategories(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FoodCategories");
        Page<FoodCategoryDTO> page = foodCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /food-categories/:id} : get the "id" foodCategory.
     *
     * @param id the id of the foodCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FoodCategoryDTO> getFoodCategory(@PathVariable Long id) {
        log.debug("REST request to get FoodCategory : {}", id);
        Optional<FoodCategoryDTO> foodCategoryDTO = foodCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodCategoryDTO);
    }

    /**
     * {@code DELETE  /food-categories/:id} : delete the "id" foodCategory.
     *
     * @param id the id of the foodCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodCategory(@PathVariable Long id) {
        log.debug("REST request to delete FoodCategory : {}", id);
        foodCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
