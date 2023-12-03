package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.CalorieInfoRepository;
import com.restaurant.angikar.service.CalorieInfoService;
import com.restaurant.angikar.service.dto.CalorieInfoDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.CalorieInfo}.
 */
@RestController
@RequestMapping("/api/calorie-infos")
public class CalorieInfoResource {

    private final Logger log = LoggerFactory.getLogger(CalorieInfoResource.class);

    private static final String ENTITY_NAME = "calorieInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalorieInfoService calorieInfoService;

    private final CalorieInfoRepository calorieInfoRepository;

    public CalorieInfoResource(CalorieInfoService calorieInfoService, CalorieInfoRepository calorieInfoRepository) {
        this.calorieInfoService = calorieInfoService;
        this.calorieInfoRepository = calorieInfoRepository;
    }

    /**
     * {@code POST  /calorie-infos} : Create a new calorieInfo.
     *
     * @param calorieInfoDTO the calorieInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calorieInfoDTO, or with status {@code 400 (Bad Request)} if the calorieInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CalorieInfoDTO> createCalorieInfo(@RequestBody CalorieInfoDTO calorieInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CalorieInfo : {}", calorieInfoDTO);
        if (calorieInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new calorieInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CalorieInfoDTO result = calorieInfoService.save(calorieInfoDTO);
        return ResponseEntity
            .created(new URI("/api/calorie-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calorie-infos/:id} : Updates an existing calorieInfo.
     *
     * @param id the id of the calorieInfoDTO to save.
     * @param calorieInfoDTO the calorieInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calorieInfoDTO,
     * or with status {@code 400 (Bad Request)} if the calorieInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calorieInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalorieInfoDTO> updateCalorieInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CalorieInfoDTO calorieInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CalorieInfo : {}, {}", id, calorieInfoDTO);
        if (calorieInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calorieInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calorieInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CalorieInfoDTO result = calorieInfoService.update(calorieInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calorieInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /calorie-infos/:id} : Partial updates given fields of an existing calorieInfo, field will ignore if it is null
     *
     * @param id the id of the calorieInfoDTO to save.
     * @param calorieInfoDTO the calorieInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calorieInfoDTO,
     * or with status {@code 400 (Bad Request)} if the calorieInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calorieInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calorieInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalorieInfoDTO> partialUpdateCalorieInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CalorieInfoDTO calorieInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CalorieInfo partially : {}, {}", id, calorieInfoDTO);
        if (calorieInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calorieInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calorieInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalorieInfoDTO> result = calorieInfoService.partialUpdate(calorieInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calorieInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /calorie-infos} : get all the calorieInfos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calorieInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CalorieInfoDTO>> getAllCalorieInfos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("mealplansettings-is-null".equals(filter)) {
            log.debug("REST request to get all CalorieInfos where mealPlanSettings is null");
            return new ResponseEntity<>(calorieInfoService.findAllWhereMealPlanSettingsIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of CalorieInfos");
        Page<CalorieInfoDTO> page = calorieInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calorie-infos/:id} : get the "id" calorieInfo.
     *
     * @param id the id of the calorieInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calorieInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalorieInfoDTO> getCalorieInfo(@PathVariable Long id) {
        log.debug("REST request to get CalorieInfo : {}", id);
        Optional<CalorieInfoDTO> calorieInfoDTO = calorieInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calorieInfoDTO);
    }

    /**
     * {@code DELETE  /calorie-infos/:id} : delete the "id" calorieInfo.
     *
     * @param id the id of the calorieInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalorieInfo(@PathVariable Long id) {
        log.debug("REST request to delete CalorieInfo : {}", id);
        calorieInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
