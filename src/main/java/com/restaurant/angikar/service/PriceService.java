package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.PriceDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Price}.
 */
public interface PriceService {
    /**
     * Save a price.
     *
     * @param priceDTO the entity to save.
     * @return the persisted entity.
     */
    PriceDTO save(PriceDTO priceDTO);

    /**
     * Updates a price.
     *
     * @param priceDTO the entity to update.
     * @return the persisted entity.
     */
    PriceDTO update(PriceDTO priceDTO);

    /**
     * Partially updates a price.
     *
     * @param priceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PriceDTO> partialUpdate(PriceDTO priceDTO);

    /**
     * Get all the prices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PriceDTO> findAll(Pageable pageable);

    /**
     * Get all the PriceDTO where Item is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PriceDTO> findAllWhereItemIsNull();

    /**
     * Get the "id" price.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PriceDTO> findOne(Long id);

    /**
     * Delete the "id" price.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
