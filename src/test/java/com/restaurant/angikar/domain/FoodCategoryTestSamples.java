package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FoodCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FoodCategory getFoodCategorySample1() {
        return new FoodCategory().id(1L).catName("catName1").description("description1").imageUrl("imageUrl1");
    }

    public static FoodCategory getFoodCategorySample2() {
        return new FoodCategory().id(2L).catName("catName2").description("description2").imageUrl("imageUrl2");
    }

    public static FoodCategory getFoodCategoryRandomSampleGenerator() {
        return new FoodCategory()
            .id(longCount.incrementAndGet())
            .catName(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString());
    }
}
