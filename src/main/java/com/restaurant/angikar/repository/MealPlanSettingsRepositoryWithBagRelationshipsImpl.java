package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlanSettings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MealPlanSettingsRepositoryWithBagRelationshipsImpl implements MealPlanSettingsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MealPlanSettings> fetchBagRelationships(Optional<MealPlanSettings> mealPlanSettings) {
        return mealPlanSettings.map(this::fetchAvoidLists);
    }

    @Override
    public Page<MealPlanSettings> fetchBagRelationships(Page<MealPlanSettings> mealPlanSettings) {
        return new PageImpl<>(
            fetchBagRelationships(mealPlanSettings.getContent()),
            mealPlanSettings.getPageable(),
            mealPlanSettings.getTotalElements()
        );
    }

    @Override
    public List<MealPlanSettings> fetchBagRelationships(List<MealPlanSettings> mealPlanSettings) {
        return Optional.of(mealPlanSettings).map(this::fetchAvoidLists).orElse(Collections.emptyList());
    }

    MealPlanSettings fetchAvoidLists(MealPlanSettings result) {
        return entityManager
            .createQuery(
                "select mealPlanSettings from MealPlanSettings mealPlanSettings left join fetch mealPlanSettings.avoidLists where mealPlanSettings.id = :id",
                MealPlanSettings.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<MealPlanSettings> fetchAvoidLists(List<MealPlanSettings> mealPlanSettings) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, mealPlanSettings.size()).forEach(index -> order.put(mealPlanSettings.get(index).getId(), index));
        List<MealPlanSettings> result = entityManager
            .createQuery(
                "select mealPlanSettings from MealPlanSettings mealPlanSettings left join fetch mealPlanSettings.avoidLists where mealPlanSettings in :mealPlanSettings",
                MealPlanSettings.class
            )
            .setParameter("mealPlanSettings", mealPlanSettings)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
