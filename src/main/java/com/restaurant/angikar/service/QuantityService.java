package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.QuantityDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Quantity}.
 */
public interface QuantityService {
    /**
     * Save a quantity.
     *
     * @param quantityDTO the entity to save.
     * @return the persisted entity.
     */
    QuantityDTO save(QuantityDTO quantityDTO);

    /**
     * Updates a quantity.
     *
     * @param quantityDTO the entity to update.
     * @return the persisted entity.
     */
    QuantityDTO update(QuantityDTO quantityDTO);

    /**
     * Partially updates a quantity.
     *
     * @param quantityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QuantityDTO> partialUpdate(QuantityDTO quantityDTO);

    /**
     * Get all the quantities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QuantityDTO> findAll(Pageable pageable);

    /**
     * Get all the QuantityDTO where Item is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<QuantityDTO> findAllWhereItemIsNull();

    /**
     * Get the "id" quantity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuantityDTO> findOne(Long id);

    /**
     * Delete the "id" quantity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
