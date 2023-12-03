package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.PincodeMasterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.PincodeMaster}.
 */
public interface PincodeMasterService {
    /**
     * Save a pincodeMaster.
     *
     * @param pincodeMasterDTO the entity to save.
     * @return the persisted entity.
     */
    PincodeMasterDTO save(PincodeMasterDTO pincodeMasterDTO);

    /**
     * Updates a pincodeMaster.
     *
     * @param pincodeMasterDTO the entity to update.
     * @return the persisted entity.
     */
    PincodeMasterDTO update(PincodeMasterDTO pincodeMasterDTO);

    /**
     * Partially updates a pincodeMaster.
     *
     * @param pincodeMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PincodeMasterDTO> partialUpdate(PincodeMasterDTO pincodeMasterDTO);

    /**
     * Get all the pincodeMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PincodeMasterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pincodeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PincodeMasterDTO> findOne(Long id);

    /**
     * Delete the "id" pincodeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
