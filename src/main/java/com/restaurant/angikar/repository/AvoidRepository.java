package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.Avoid;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Avoid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvoidRepository extends JpaRepository<Avoid, Long> {}
