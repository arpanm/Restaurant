package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.PincodeMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PincodeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PincodeMasterRepository extends JpaRepository<PincodeMaster, Long> {}
