package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.NutritionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Nutrition}.
 */
public interface NutritionService {
    /**
     * Save a nutrition.
     *
     * @param nutritionDTO the entity to save.
     * @return the persisted entity.
     */
    NutritionDTO save(NutritionDTO nutritionDTO);

    /**
     * Updates a nutrition.
     *
     * @param nutritionDTO the entity to update.
     * @return the persisted entity.
     */
    NutritionDTO update(NutritionDTO nutritionDTO);

    /**
     * Partially updates a nutrition.
     *
     * @param nutritionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NutritionDTO> partialUpdate(NutritionDTO nutritionDTO);

    /**
     * Get all the nutritions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NutritionDTO> findAll(Pageable pageable);

    /**
     * Get all the NutritionDTO where Item is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<NutritionDTO> findAllWhereItemIsNull();

    /**
     * Get the "id" nutrition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NutritionDTO> findOne(Long id);

    /**
     * Delete the "id" nutrition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
