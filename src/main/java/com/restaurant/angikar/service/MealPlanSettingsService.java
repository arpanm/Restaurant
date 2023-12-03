package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.MealPlanSettingsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.MealPlanSettings}.
 */
public interface MealPlanSettingsService {
    /**
     * Save a mealPlanSettings.
     *
     * @param mealPlanSettingsDTO the entity to save.
     * @return the persisted entity.
     */
    MealPlanSettingsDTO save(MealPlanSettingsDTO mealPlanSettingsDTO);

    /**
     * Updates a mealPlanSettings.
     *
     * @param mealPlanSettingsDTO the entity to update.
     * @return the persisted entity.
     */
    MealPlanSettingsDTO update(MealPlanSettingsDTO mealPlanSettingsDTO);

    /**
     * Partially updates a mealPlanSettings.
     *
     * @param mealPlanSettingsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MealPlanSettingsDTO> partialUpdate(MealPlanSettingsDTO mealPlanSettingsDTO);

    /**
     * Get all the mealPlanSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MealPlanSettingsDTO> findAll(Pageable pageable);

    /**
     * Get all the mealPlanSettings with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MealPlanSettingsDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" mealPlanSettings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MealPlanSettingsDTO> findOne(Long id);

    /**
     * Delete the "id" mealPlanSettings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
