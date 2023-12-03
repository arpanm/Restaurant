package com.restaurant.angikar.repository;

import com.restaurant.angikar.domain.Coupon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Coupon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {}
