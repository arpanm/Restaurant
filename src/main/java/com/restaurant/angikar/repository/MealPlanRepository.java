package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MealPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {}
