package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.IngredienceMasterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.IngredienceMaster}.
 */
public interface IngredienceMasterService {
    /**
     * Save a ingredienceMaster.
     *
     * @param ingredienceMasterDTO the entity to save.
     * @return the persisted entity.
     */
    IngredienceMasterDTO save(IngredienceMasterDTO ingredienceMasterDTO);

    /**
     * Updates a ingredienceMaster.
     *
     * @param ingredienceMasterDTO the entity to update.
     * @return the persisted entity.
     */
    IngredienceMasterDTO update(IngredienceMasterDTO ingredienceMasterDTO);

    /**
     * Partially updates a ingredienceMaster.
     *
     * @param ingredienceMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IngredienceMasterDTO> partialUpdate(IngredienceMasterDTO ingredienceMasterDTO);

    /**
     * Get all the ingredienceMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IngredienceMasterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ingredienceMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IngredienceMasterDTO> findOne(Long id);

    /**
     * Delete the "id" ingredienceMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
