package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RestaurantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Restaurant getRestaurantSample1() {
        return new Restaurant().id(1L).restaurantName("restaurantName1").title("title1").logo("logo1");
    }

    public static Restaurant getRestaurantSample2() {
        return new Restaurant().id(2L).restaurantName("restaurantName2").title("title2").logo("logo2");
    }

    public static Restaurant getRestaurantRandomSampleGenerator() {
        return new Restaurant()
            .id(longCount.incrementAndGet())
            .restaurantName(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .logo(UUID.randomUUID().toString());
    }
}
