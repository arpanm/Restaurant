package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlanSettings;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MealPlanSettingsRepositoryWithBagRelationships {
    Optional<MealPlanSettings> fetchBagRelationships(Optional<MealPlanSettings> mealPlanSettings);

    List<MealPlanSettings> fetchBagRelationships(List<MealPlanSettings> mealPlanSettings);

    Page<MealPlanSettings> fetchBagRelationships(Page<MealPlanSettings> mealPlanSettings);
}
