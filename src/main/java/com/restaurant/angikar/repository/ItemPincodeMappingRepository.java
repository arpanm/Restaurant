package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.ItemPincodeMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ItemPincodeMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPincodeMappingRepository extends JpaRepository<ItemPincodeMapping, Long> {}
