package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MealPlanItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MealPlanItem getMealPlanItemSample1() {
        return new MealPlanItem().id(1L).planItemName("planItemName1").hourValue(1).pincode("pincode1");
    }

    public static MealPlanItem getMealPlanItemSample2() {
        return new MealPlanItem().id(2L).planItemName("planItemName2").hourValue(2).pincode("pincode2");
    }

    public static MealPlanItem getMealPlanItemRandomSampleGenerator() {
        return new MealPlanItem()
            .id(longCount.incrementAndGet())
            .planItemName(UUID.randomUUID().toString())
            .hourValue(intCount.incrementAndGet())
            .pincode(UUID.randomUUID().toString());
    }
}
