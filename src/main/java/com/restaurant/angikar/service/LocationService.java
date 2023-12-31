package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.LocationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Location}.
 */
public interface LocationService {
    /**
     * Save a location.
     *
     * @param locationDTO the entity to save.
     * @return the persisted entity.
     */
    LocationDTO save(LocationDTO locationDTO);

    /**
     * Updates a location.
     *
     * @param locationDTO the entity to update.
     * @return the persisted entity.
     */
    LocationDTO update(LocationDTO locationDTO);

    /**
     * Partially updates a location.
     *
     * @param locationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationDTO> partialUpdate(LocationDTO locationDTO);

    /**
     * Get all the locations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocationDTO> findAll(Pageable pageable);

    /**
     * Get all the LocationDTO where Restaurant is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereRestaurantIsNull();
    /**
     * Get all the LocationDTO where MealPlanItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereMealPlanItemIsNull();
    /**
     * Get all the LocationDTO where OrderItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereOrderItemIsNull();
    /**
     * Get all the LocationDTO where Order is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LocationDTO> findAllWhereOrderIsNull();

    /**
     * Get the "id" location.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationDTO> findOne(Long id);

    /**
     * Delete the "id" location.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
