package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.WeightInfoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.WeightInfo}.
 */
public interface WeightInfoService {
    /**
     * Save a weightInfo.
     *
     * @param weightInfoDTO the entity to save.
     * @return the persisted entity.
     */
    WeightInfoDTO save(WeightInfoDTO weightInfoDTO);

    /**
     * Updates a weightInfo.
     *
     * @param weightInfoDTO the entity to update.
     * @return the persisted entity.
     */
    WeightInfoDTO update(WeightInfoDTO weightInfoDTO);

    /**
     * Partially updates a weightInfo.
     *
     * @param weightInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WeightInfoDTO> partialUpdate(WeightInfoDTO weightInfoDTO);

    /**
     * Get all the weightInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WeightInfoDTO> findAll(Pageable pageable);

    /**
     * Get all the WeightInfoDTO where MealPlanSettings is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<WeightInfoDTO> findAllWhereMealPlanSettingsIsNull();

    /**
     * Get the "id" weightInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WeightInfoDTO> findOne(Long id);

    /**
     * Delete the "id" weightInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
