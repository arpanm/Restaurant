package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.MealPlanSettings;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MealPlanSettings entity.
 *
 * When extending this class, extend MealPlanSettingsRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface MealPlanSettingsRepository extends MealPlanSettingsRepositoryWithBagRelationships, JpaRepository<MealPlanSettings, Long> {
    default Optional<MealPlanSettings> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<MealPlanSettings> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<MealPlanSettings> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
