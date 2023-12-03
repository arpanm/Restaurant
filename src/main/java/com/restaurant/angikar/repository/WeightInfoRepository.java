package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.WeightInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WeightInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeightInfoRepository extends JpaRepository<WeightInfo, Long> {}
