package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.SkipDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SkipDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkipDateRepository extends JpaRepository<SkipDate, Long> {}
