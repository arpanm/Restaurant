package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlanItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MealPlanItemRepositoryWithBagRelationships {
    Optional<MealPlanItem> fetchBagRelationships(Optional<MealPlanItem> mealPlanItem);

    List<MealPlanItem> fetchBagRelationships(List<MealPlanItem> mealPlanItems);

    Page<MealPlanItem> fetchBagRelationships(Page<MealPlanItem> mealPlanItems);
}
