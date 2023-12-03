package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.WeightInfoRepository;
import com.restaurant.angikar.service.WeightInfoService;
import com.restaurant.angikar.service.dto.WeightInfoDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.WeightInfo}.
 */
@RestController
@RequestMapping("/api/weight-infos")
public class WeightInfoResource {

    private final Logger log = LoggerFactory.getLogger(WeightInfoResource.class);

    private static final String ENTITY_NAME = "weightInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeightInfoService weightInfoService;

    private final WeightInfoRepository weightInfoRepository;

    public WeightInfoResource(WeightInfoService weightInfoService, WeightInfoRepository weightInfoRepository) {
        this.weightInfoService = weightInfoService;
        this.weightInfoRepository = weightInfoRepository;
    }

    /**
     * {@code POST  /weight-infos} : Create a new weightInfo.
     *
     * @param weightInfoDTO the weightInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weightInfoDTO, or with status {@code 400 (Bad Request)} if the weightInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WeightInfoDTO> createWeightInfo(@RequestBody WeightInfoDTO weightInfoDTO) throws URISyntaxException {
        log.debug("REST request to save WeightInfo : {}", weightInfoDTO);
        if (weightInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new weightInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeightInfoDTO result = weightInfoService.save(weightInfoDTO);
        return ResponseEntity
            .created(new URI("/api/weight-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weight-infos/:id} : Updates an existing weightInfo.
     *
     * @param id the id of the weightInfoDTO to save.
     * @param weightInfoDTO the weightInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weightInfoDTO,
     * or with status {@code 400 (Bad Request)} if the weightInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weightInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WeightInfoDTO> updateWeightInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WeightInfoDTO weightInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WeightInfo : {}, {}", id, weightInfoDTO);
        if (weightInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weightInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WeightInfoDTO result = weightInfoService.update(weightInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weightInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /weight-infos/:id} : Partial updates given fields of an existing weightInfo, field will ignore if it is null
     *
     * @param id the id of the weightInfoDTO to save.
     * @param weightInfoDTO the weightInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weightInfoDTO,
     * or with status {@code 400 (Bad Request)} if the weightInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the weightInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the weightInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WeightInfoDTO> partialUpdateWeightInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody WeightInfoDTO weightInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WeightInfo partially : {}, {}", id, weightInfoDTO);
        if (weightInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, weightInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!weightInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WeightInfoDTO> result = weightInfoService.partialUpdate(weightInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, weightInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /weight-infos} : get all the weightInfos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weightInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WeightInfoDTO>> getAllWeightInfos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("mealplansettings-is-null".equals(filter)) {
            log.debug("REST request to get all WeightInfos where mealPlanSettings is null");
            return new ResponseEntity<>(weightInfoService.findAllWhereMealPlanSettingsIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of WeightInfos");
        Page<WeightInfoDTO> page = weightInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /weight-infos/:id} : get the "id" weightInfo.
     *
     * @param id the id of the weightInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weightInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WeightInfoDTO> getWeightInfo(@PathVariable Long id) {
        log.debug("REST request to get WeightInfo : {}", id);
        Optional<WeightInfoDTO> weightInfoDTO = weightInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weightInfoDTO);
    }

    /**
     * {@code DELETE  /weight-infos/:id} : delete the "id" weightInfo.
     *
     * @param id the id of the weightInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeightInfo(@PathVariable Long id) {
        log.debug("REST request to delete WeightInfo : {}", id);
        weightInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
