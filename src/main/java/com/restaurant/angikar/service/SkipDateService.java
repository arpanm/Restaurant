package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.SkipDateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.SkipDate}.
 */
public interface SkipDateService {
    /**
     * Save a skipDate.
     *
     * @param skipDateDTO the entity to save.
     * @return the persisted entity.
     */
    SkipDateDTO save(SkipDateDTO skipDateDTO);

    /**
     * Updates a skipDate.
     *
     * @param skipDateDTO the entity to update.
     * @return the persisted entity.
     */
    SkipDateDTO update(SkipDateDTO skipDateDTO);

    /**
     * Partially updates a skipDate.
     *
     * @param skipDateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SkipDateDTO> partialUpdate(SkipDateDTO skipDateDTO);

    /**
     * Get all the skipDates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkipDateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" skipDate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkipDateDTO> findOne(Long id);

    /**
     * Delete the "id" skipDate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
