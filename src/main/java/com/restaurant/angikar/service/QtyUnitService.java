package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.QtyUnitDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.QtyUnit}.
 */
public interface QtyUnitService {
    /**
     * Save a qtyUnit.
     *
     * @param qtyUnitDTO the entity to save.
     * @return the persisted entity.
     */
    QtyUnitDTO save(QtyUnitDTO qtyUnitDTO);

    /**
     * Updates a qtyUnit.
     *
     * @param qtyUnitDTO the entity to update.
     * @return the persisted entity.
     */
    QtyUnitDTO update(QtyUnitDTO qtyUnitDTO);

    /**
     * Partially updates a qtyUnit.
     *
     * @param qtyUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<QtyUnitDTO> partialUpdate(QtyUnitDTO qtyUnitDTO);

    /**
     * Get all the qtyUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<QtyUnitDTO> findAll(Pageable pageable);

    /**
     * Get all the QtyUnitDTO where Quantity is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<QtyUnitDTO> findAllWhereQuantityIsNull();

    /**
     * Get the "id" qtyUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QtyUnitDTO> findOne(Long id);

    /**
     * Delete the "id" qtyUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
