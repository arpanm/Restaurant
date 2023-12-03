package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SkipDateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SkipDate getSkipDateSample1() {
        return new SkipDate().id(1L);
    }

    public static SkipDate getSkipDateSample2() {
        return new SkipDate().id(2L);
    }

    public static SkipDate getSkipDateRandomSampleGenerator() {
        return new SkipDate().id(longCount.incrementAndGet());
    }
}
