package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.AvoidDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Avoid}.
 */
public interface AvoidService {
    /**
     * Save a avoid.
     *
     * @param avoidDTO the entity to save.
     * @return the persisted entity.
     */
    AvoidDTO save(AvoidDTO avoidDTO);

    /**
     * Updates a avoid.
     *
     * @param avoidDTO the entity to update.
     * @return the persisted entity.
     */
    AvoidDTO update(AvoidDTO avoidDTO);

    /**
     * Partially updates a avoid.
     *
     * @param avoidDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AvoidDTO> partialUpdate(AvoidDTO avoidDTO);

    /**
     * Get all the avoids.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AvoidDTO> findAll(Pageable pageable);

    /**
     * Get the "id" avoid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AvoidDTO> findOne(Long id);

    /**
     * Delete the "id" avoid.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
