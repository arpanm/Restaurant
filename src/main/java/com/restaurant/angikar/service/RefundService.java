package com.restaurant.angikar.service;

import com.restaurant.angikar.service.dto.RefundDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.restaurant.angikar.domain.Refund}.
 */
public interface RefundService {
    /**
     * Save a refund.
     *
     * @param refundDTO the entity to save.
     * @return the persisted entity.
     */
    RefundDTO save(RefundDTO refundDTO);

    /**
     * Updates a refund.
     *
     * @param refundDTO the entity to update.
     * @return the persisted entity.
     */
    RefundDTO update(RefundDTO refundDTO);

    /**
     * Partially updates a refund.
     *
     * @param refundDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RefundDTO> partialUpdate(RefundDTO refundDTO);

    /**
     * Get all the refunds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RefundDTO> findAll(Pageable pageable);

    /**
     * Get the "id" refund.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RefundDTO> findOne(Long id);

    /**
     * Delete the "id" refund.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
