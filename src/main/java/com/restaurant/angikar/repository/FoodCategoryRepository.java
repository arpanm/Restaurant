package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.FoodCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FoodCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {}
