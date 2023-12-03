package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class QtyUnitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static QtyUnit getQtyUnitSample1() {
        return new QtyUnit().id(1L).unitName("unitName1");
    }

    public static QtyUnit getQtyUnitSample2() {
        return new QtyUnit().id(2L).unitName("unitName2");
    }

    public static QtyUnit getQtyUnitRandomSampleGenerator() {
        return new QtyUnit().id(longCount.incrementAndGet()).unitName(UUID.randomUUID().toString());
    }
}
