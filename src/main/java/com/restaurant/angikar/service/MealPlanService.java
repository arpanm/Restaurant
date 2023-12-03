package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.MealPlanDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.MealPlan}.
 */
public interface MealPlanService {
    /**
     * Save a mealPlan.
     *
     * @param mealPlanDTO the entity to save.
     * @return the persisted entity.
     */
    MealPlanDTO save(MealPlanDTO mealPlanDTO);

    /**
     * Updates a mealPlan.
     *
     * @param mealPlanDTO the entity to update.
     * @return the persisted entity.
     */
    MealPlanDTO update(MealPlanDTO mealPlanDTO);

    /**
     * Partially updates a mealPlan.
     *
     * @param mealPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MealPlanDTO> partialUpdate(MealPlanDTO mealPlanDTO);

    /**
     * Get all the mealPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MealPlanDTO> findAll(Pageable pageable);

    /**
     * Get all the MealPlanDTO where CartItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MealPlanDTO> findAllWhereCartItemIsNull();
    /**
     * Get all the MealPlanDTO where OrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MealPlanDTO> findAllWhereOrderItemIsNull();

    /**
     * Get the "id" mealPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MealPlanDTO> findOne(Long id);

    /**
     * Delete the "id" mealPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
