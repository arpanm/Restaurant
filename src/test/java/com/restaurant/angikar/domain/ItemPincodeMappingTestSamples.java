package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ItemPincodeMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ItemPincodeMapping getItemPincodeMappingSample1() {
        return new ItemPincodeMapping().id(1L).serviceability("serviceability1").pincode("pincode1");
    }

    public static ItemPincodeMapping getItemPincodeMappingSample2() {
        return new ItemPincodeMapping().id(2L).serviceability("serviceability2").pincode("pincode2");
    }

    public static ItemPincodeMapping getItemPincodeMappingRandomSampleGenerator() {
        return new ItemPincodeMapping()
            .id(longCount.incrementAndGet())
            .serviceability(UUID.randomUUID().toString())
            .pincode(UUID.randomUUID().toString());
    }
}
