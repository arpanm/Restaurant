package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlanItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MealPlanItem entity.
 *
 * When extending this class, extend MealPlanItemRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface MealPlanItemRepository extends MealPlanItemRepositoryWithBagRelationships, JpaRepository<MealPlanItem, Long> {
    default Optional<MealPlanItem> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<MealPlanItem> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<MealPlanItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
