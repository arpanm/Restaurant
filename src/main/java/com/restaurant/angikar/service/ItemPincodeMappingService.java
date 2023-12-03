package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.ItemPincodeMappingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.ItemPincodeMapping}.
 */
public interface ItemPincodeMappingService {
    /**
     * Save a itemPincodeMapping.
     *
     * @param itemPincodeMappingDTO the entity to save.
     * @return the persisted entity.
     */
    ItemPincodeMappingDTO save(ItemPincodeMappingDTO itemPincodeMappingDTO);

    /**
     * Updates a itemPincodeMapping.
     *
     * @param itemPincodeMappingDTO the entity to update.
     * @return the persisted entity.
     */
    ItemPincodeMappingDTO update(ItemPincodeMappingDTO itemPincodeMappingDTO);

    /**
     * Partially updates a itemPincodeMapping.
     *
     * @param itemPincodeMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemPincodeMappingDTO> partialUpdate(ItemPincodeMappingDTO itemPincodeMappingDTO);

    /**
     * Get all the itemPincodeMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemPincodeMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" itemPincodeMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemPincodeMappingDTO> findOne(Long id);

    /**
     * Delete the "id" itemPincodeMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
