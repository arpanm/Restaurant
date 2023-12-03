package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.CalorieInfoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.CalorieInfo}.
 */
public interface CalorieInfoService {
    /**
     * Save a calorieInfo.
     *
     * @param calorieInfoDTO the entity to save.
     * @return the persisted entity.
     */
    CalorieInfoDTO save(CalorieInfoDTO calorieInfoDTO);

    /**
     * Updates a calorieInfo.
     *
     * @param calorieInfoDTO the entity to update.
     * @return the persisted entity.
     */
    CalorieInfoDTO update(CalorieInfoDTO calorieInfoDTO);

    /**
     * Partially updates a calorieInfo.
     *
     * @param calorieInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CalorieInfoDTO> partialUpdate(CalorieInfoDTO calorieInfoDTO);

    /**
     * Get all the calorieInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalorieInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the CalorieInfoDTO where MealPlanSettings is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CalorieInfoDTO> findAllWhereMealPlanSettingsIsNull();

    /**
     * Get the "id" calorieInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalorieInfoDTO> findOne(Long id);

    /**
     * Delete the "id" calorieInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
