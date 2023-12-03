package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NutritionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nutrition getNutritionSample1() {
        return new Nutrition().id(1L);
    }

    public static Nutrition getNutritionSample2() {
        return new Nutrition().id(2L);
    }

    public static Nutrition getNutritionRandomSampleGenerator() {
        return new Nutrition().id(longCount.incrementAndGet());
    }
}
