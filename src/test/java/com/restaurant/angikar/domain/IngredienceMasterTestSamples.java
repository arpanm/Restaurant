package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IngredienceMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static IngredienceMaster getIngredienceMasterSample1() {
        return new IngredienceMaster().id(1L).ingredience("ingredience1");
    }

    public static IngredienceMaster getIngredienceMasterSample2() {
        return new IngredienceMaster().id(2L).ingredience("ingredience2");
    }

    public static IngredienceMaster getIngredienceMasterRandomSampleGenerator() {
        return new IngredienceMaster().id(longCount.incrementAndGet()).ingredience(UUID.randomUUID().toString());
    }
}
