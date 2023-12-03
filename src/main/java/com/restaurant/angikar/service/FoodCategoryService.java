package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.FoodCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.FoodCategory}.
 */
public interface FoodCategoryService {
    /**
     * Save a foodCategory.
     *
     * @param foodCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    FoodCategoryDTO save(FoodCategoryDTO foodCategoryDTO);

    /**
     * Updates a foodCategory.
     *
     * @param foodCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    FoodCategoryDTO update(FoodCategoryDTO foodCategoryDTO);

    /**
     * Partially updates a foodCategory.
     *
     * @param foodCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FoodCategoryDTO> partialUpdate(FoodCategoryDTO foodCategoryDTO);

    /**
     * Get all the foodCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoodCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" foodCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoodCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" foodCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
