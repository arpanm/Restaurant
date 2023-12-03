package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.NutritionRepository;
import com.restaurant.angikar.service.NutritionService;
import com.restaurant.angikar.service.dto.NutritionDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.Nutrition}.
 */
@RestController
@RequestMapping("/api/nutritions")
public class NutritionResource {

    private final Logger log = LoggerFactory.getLogger(NutritionResource.class);

    private static final String ENTITY_NAME = "nutrition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NutritionService nutritionService;

    private final NutritionRepository nutritionRepository;

    public NutritionResource(NutritionService nutritionService, NutritionRepository nutritionRepository) {
        this.nutritionService = nutritionService;
        this.nutritionRepository = nutritionRepository;
    }

    /**
     * {@code POST  /nutritions} : Create a new nutrition.
     *
     * @param nutritionDTO the nutritionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nutritionDTO, or with status {@code 400 (Bad Request)} if the nutrition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NutritionDTO> createNutrition(@RequestBody NutritionDTO nutritionDTO) throws URISyntaxException {
        log.debug("REST request to save Nutrition : {}", nutritionDTO);
        if (nutritionDTO.getId() != null) {
            throw new BadRequestAlertException("A new nutrition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NutritionDTO result = nutritionService.save(nutritionDTO);
        return ResponseEntity
            .created(new URI("/api/nutritions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nutritions/:id} : Updates an existing nutrition.
     *
     * @param id the id of the nutritionDTO to save.
     * @param nutritionDTO the nutritionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionDTO,
     * or with status {@code 400 (Bad Request)} if the nutritionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nutritionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NutritionDTO> updateNutrition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NutritionDTO nutritionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nutrition : {}, {}", id, nutritionDTO);
        if (nutritionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NutritionDTO result = nutritionService.update(nutritionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nutritions/:id} : Partial updates given fields of an existing nutrition, field will ignore if it is null
     *
     * @param id the id of the nutritionDTO to save.
     * @param nutritionDTO the nutritionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutritionDTO,
     * or with status {@code 400 (Bad Request)} if the nutritionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nutritionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nutritionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NutritionDTO> partialUpdateNutrition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NutritionDTO nutritionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nutrition partially : {}, {}", id, nutritionDTO);
        if (nutritionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nutritionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nutritionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NutritionDTO> result = nutritionService.partialUpdate(nutritionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutritionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nutritions} : get all the nutritions.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nutritions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<NutritionDTO>> getAllNutritions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("item-is-null".equals(filter)) {
            log.debug("REST request to get all Nutritions where item is null");
            return new ResponseEntity<>(nutritionService.findAllWhereItemIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Nutritions");
        Page<NutritionDTO> page = nutritionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nutritions/:id} : get the "id" nutrition.
     *
     * @param id the id of the nutritionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nutritionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NutritionDTO> getNutrition(@PathVariable Long id) {
        log.debug("REST request to get Nutrition : {}", id);
        Optional<NutritionDTO> nutritionDTO = nutritionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nutritionDTO);
    }

    /**
     * {@code DELETE  /nutritions/:id} : delete the "id" nutrition.
     *
     * @param id the id of the nutritionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutrition(@PathVariable Long id) {
        log.debug("REST request to delete Nutrition : {}", id);
        nutritionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
