package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.QtyUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QtyUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QtyUnitRepository extends JpaRepository<QtyUnit, Long> {}
