package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class WeightInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static WeightInfo getWeightInfoSample1() {
        return new WeightInfo().id(1L).numberOfDays(1);
    }

    public static WeightInfo getWeightInfoSample2() {
        return new WeightInfo().id(2L).numberOfDays(2);
    }

    public static WeightInfo getWeightInfoRandomSampleGenerator() {
        return new WeightInfo().id(longCount.incrementAndGet()).numberOfDays(intCount.incrementAndGet());
    }
}
