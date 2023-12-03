package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CalorieInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CalorieInfo getCalorieInfoSample1() {
        return new CalorieInfo().id(1L);
    }

    public static CalorieInfo getCalorieInfoSample2() {
        return new CalorieInfo().id(2L);
    }

    public static CalorieInfo getCalorieInfoRandomSampleGenerator() {
        return new CalorieInfo().id(longCount.incrementAndGet());
    }
}
