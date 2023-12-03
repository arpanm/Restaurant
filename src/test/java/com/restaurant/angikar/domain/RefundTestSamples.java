package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RefundTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Refund getRefundSample1() {
        return new Refund().id(1L);
    }

    public static Refund getRefundSample2() {
        return new Refund().id(2L);
    }

    public static Refund getRefundRandomSampleGenerator() {
        return new Refund().id(longCount.incrementAndGet());
    }
}
