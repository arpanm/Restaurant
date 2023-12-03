package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.CouponDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Coupon}.
 */
public interface CouponService {
    /**
     * Save a coupon.
     *
     * @param couponDTO the entity to save.
     * @return the persisted entity.
     */
    CouponDTO save(CouponDTO couponDTO);

    /**
     * Updates a coupon.
     *
     * @param couponDTO the entity to update.
     * @return the persisted entity.
     */
    CouponDTO update(CouponDTO couponDTO);

    /**
     * Partially updates a coupon.
     *
     * @param couponDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CouponDTO> partialUpdate(CouponDTO couponDTO);

    /**
     * Get all the coupons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CouponDTO> findAll(Pageable pageable);

    /**
     * Get all the CouponDTO where Cart is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CouponDTO> findAllWhereCartIsNull();

    /**
     * Get the "id" coupon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CouponDTO> findOne(Long id);

    /**
     * Delete the "id" coupon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
