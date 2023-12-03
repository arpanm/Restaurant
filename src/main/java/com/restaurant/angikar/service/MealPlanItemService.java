package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.MealPlanItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.MealPlanItem}.
 */
public interface MealPlanItemService {
    /**
     * Save a mealPlanItem.
     *
     * @param mealPlanItemDTO the entity to save.
     * @return the persisted entity.
     */
    MealPlanItemDTO save(MealPlanItemDTO mealPlanItemDTO);

    /**
     * Updates a mealPlanItem.
     *
     * @param mealPlanItemDTO the entity to update.
     * @return the persisted entity.
     */
    MealPlanItemDTO update(MealPlanItemDTO mealPlanItemDTO);

    /**
     * Partially updates a mealPlanItem.
     *
     * @param mealPlanItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MealPlanItemDTO> partialUpdate(MealPlanItemDTO mealPlanItemDTO);

    /**
     * Get all the mealPlanItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MealPlanItemDTO> findAll(Pageable pageable);

    /**
     * Get all the mealPlanItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MealPlanItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mealPlanItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MealPlanItemDTO> findOne(Long id);

    /**
     * Delete the "id" mealPlanItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
