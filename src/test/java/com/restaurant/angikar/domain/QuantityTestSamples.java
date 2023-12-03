package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class QuantityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Quantity getQuantitySample1() {
        return new Quantity().id(1L);
    }

    public static Quantity getQuantitySample2() {
        return new Quantity().id(2L);
    }

    public static Quantity getQuantityRandomSampleGenerator() {
        return new Quantity().id(longCount.incrementAndGet());
    }
}
