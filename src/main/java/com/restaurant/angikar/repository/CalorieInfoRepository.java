package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.CalorieInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CalorieInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalorieInfoRepository extends JpaRepository<CalorieInfo, Long> {}
