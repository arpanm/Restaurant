package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MealPlanSettingsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MealPlanSettings getMealPlanSettingsSample1() {
        return new MealPlanSettings().id(1L);
    }

    public static MealPlanSettings getMealPlanSettingsSample2() {
        return new MealPlanSettings().id(2L);
    }

    public static MealPlanSettings getMealPlanSettingsRandomSampleGenerator() {
        return new MealPlanSettings().id(longCount.incrementAndGet());
    }
}
