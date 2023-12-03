package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.DiscountDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Discount}.
 */
public interface DiscountService {
    /**
     * Save a discount.
     *
     * @param discountDTO the entity to save.
     * @return the persisted entity.
     */
    DiscountDTO save(DiscountDTO discountDTO);

    /**
     * Updates a discount.
     *
     * @param discountDTO the entity to update.
     * @return the persisted entity.
     */
    DiscountDTO update(DiscountDTO discountDTO);

    /**
     * Partially updates a discount.
     *
     * @param discountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiscountDTO> partialUpdate(DiscountDTO discountDTO);

    /**
     * Get all the discounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DiscountDTO> findAll(Pageable pageable);

    /**
     * Get all the DiscountDTO where MealPlan is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DiscountDTO> findAllWhereMealPlanIsNull();

    /**
     * Get the "id" discount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountDTO> findOne(Long id);

    /**
     * Delete the "id" discount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
