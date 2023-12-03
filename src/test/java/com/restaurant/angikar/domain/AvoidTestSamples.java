package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AvoidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Avoid getAvoidSample1() {
        return new Avoid().id(1L).avoidIngredience("avoidIngredience1");
    }

    public static Avoid getAvoidSample2() {
        return new Avoid().id(2L).avoidIngredience("avoidIngredience2");
    }

    public static Avoid getAvoidRandomSampleGenerator() {
        return new Avoid().id(longCount.incrementAndGet()).avoidIngredience(UUID.randomUUID().toString());
    }
}
