package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlanItem;
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
public class MealPlanItemRepositoryWithBagRelationshipsImpl implements MealPlanItemRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MealPlanItem> fetchBagRelationships(Optional<MealPlanItem> mealPlanItem) {
        return mealPlanItem.map(this::fetchItems);
    }

    @Override
    public Page<MealPlanItem> fetchBagRelationships(Page<MealPlanItem> mealPlanItems) {
        return new PageImpl<>(
            fetchBagRelationships(mealPlanItems.getContent()),
            mealPlanItems.getPageable(),
            mealPlanItems.getTotalElements()
        );
    }

    @Override
    public List<MealPlanItem> fetchBagRelationships(List<MealPlanItem> mealPlanItems) {
        return Optional.of(mealPlanItems).map(this::fetchItems).orElse(Collections.emptyList());
    }

    MealPlanItem fetchItems(MealPlanItem result) {
        return entityManager
            .createQuery(
                "select mealPlanItem from MealPlanItem mealPlanItem left join fetch mealPlanItem.items where mealPlanItem.id = :id",
                MealPlanItem.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<MealPlanItem> fetchItems(List<MealPlanItem> mealPlanItems) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, mealPlanItems.size()).forEach(index -> order.put(mealPlanItems.get(index).getId(), index));
        List<MealPlanItem> result = entityManager
            .createQuery(
                "select mealPlanItem from MealPlanItem mealPlanItem left join fetch mealPlanItem.items where mealPlanItem in :mealPlanItems",
                MealPlanItem.class
            )
            .setParameter("mealPlanItems", mealPlanItems)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
