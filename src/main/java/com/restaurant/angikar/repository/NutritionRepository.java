package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.Nutrition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nutrition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long> {}
