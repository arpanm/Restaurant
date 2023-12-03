package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MealPlanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MealPlan getMealPlanSample1() {
        return new MealPlan().id(1L).planName("planName1");
    }

    public static MealPlan getMealPlanSample2() {
        return new MealPlan().id(2L).planName("planName2");
    }

    public static MealPlan getMealPlanRandomSampleGenerator() {
        return new MealPlan().id(longCount.incrementAndGet()).planName(UUID.randomUUID().toString());
    }
}
