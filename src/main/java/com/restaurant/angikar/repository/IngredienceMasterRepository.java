package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.IngredienceMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IngredienceMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredienceMasterRepository extends JpaRepository<IngredienceMaster, Long> {}
