package com.restaurant.angikar.web.rest;

import com.restaurant.angikar.repository.CouponRepository;
import com.restaurant.angikar.service.CouponService;
import com.restaurant.angikar.service.dto.CouponDTO;
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
 * REST controller for managing {@link com.restaurant.angikar.domain.Coupon}.
 */
@RestController
@RequestMapping("/api/coupons")
public class CouponResource {

    private final Logger log = LoggerFactory.getLogger(CouponResource.class);

    private static final String ENTITY_NAME = "coupon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouponService couponService;

    private final CouponRepository couponRepository;

    public CouponResource(CouponService couponService, CouponRepository couponRepository) {
        this.couponService = couponService;
        this.couponRepository = couponRepository;
    }

    /**
     * {@code POST  /coupons} : Create a new coupon.
     *
     * @param couponDTO the couponDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new couponDTO, or with status {@code 400 (Bad Request)} if the coupon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CouponDTO> createCoupon(@RequestBody CouponDTO couponDTO) throws URISyntaxException {
        log.debug("REST request to save Coupon : {}", couponDTO);
        if (couponDTO.getId() != null) {
            throw new BadRequestAlertException("A new coupon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CouponDTO result = couponService.save(couponDTO);
        return ResponseEntity
            .created(new URI("/api/coupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coupons/:id} : Updates an existing coupon.
     *
     * @param id the id of the couponDTO to save.
     * @param couponDTO the couponDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated couponDTO,
     * or with status {@code 400 (Bad Request)} if the couponDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the couponDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CouponDTO> updateCoupon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CouponDTO couponDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Coupon : {}, {}", id, couponDTO);
        if (couponDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, couponDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!couponRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CouponDTO result = couponService.update(couponDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, couponDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coupons/:id} : Partial updates given fields of an existing coupon, field will ignore if it is null
     *
     * @param id the id of the couponDTO to save.
     * @param couponDTO the couponDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated couponDTO,
     * or with status {@code 400 (Bad Request)} if the couponDTO is not valid,
     * or with status {@code 404 (Not Found)} if the couponDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the couponDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CouponDTO> partialUpdateCoupon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CouponDTO couponDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Coupon partially : {}, {}", id, couponDTO);
        if (couponDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, couponDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!couponRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CouponDTO> result = couponService.partialUpdate(couponDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, couponDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /coupons} : get all the coupons.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coupons in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CouponDTO>> getAllCoupons(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("cart-is-null".equals(filter)) {
            log.debug("REST request to get all Coupons where cart is null");
            return new ResponseEntity<>(couponService.findAllWhereCartIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Coupons");
        Page<CouponDTO> page = couponService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coupons/:id} : get the "id" coupon.
     *
     * @param id the id of the couponDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the couponDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CouponDTO> getCoupon(@PathVariable Long id) {
        log.debug("REST request to get Coupon : {}", id);
        Optional<CouponDTO> couponDTO = couponService.findOne(id);
        return ResponseUtil.wrapOrNotFound(couponDTO);
    }

    /**
     * {@code DELETE  /coupons/:id} : delete the "id" coupon.
     *
     * @param id the id of the couponDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        log.debug("REST request to delete Coupon : {}", id);
        couponService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
