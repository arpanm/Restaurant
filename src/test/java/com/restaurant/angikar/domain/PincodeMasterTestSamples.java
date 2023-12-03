package com.restaurant.angikar.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PincodeMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PincodeMaster getPincodeMasterSample1() {
        return new PincodeMaster()
            .id(1L)
            .pincode("pincode1")
            .area("area1")
            .city("city1")
            .stateProvince("stateProvince1")
            .country("country1");
    }

    public static PincodeMaster getPincodeMasterSample2() {
        return new PincodeMaster()
            .id(2L)
            .pincode("pincode2")
            .area("area2")
            .city("city2")
            .stateProvince("stateProvince2")
            .country("country2");
    }

    public static PincodeMaster getPincodeMasterRandomSampleGenerator() {
        return new PincodeMaster()
            .id(longCount.incrementAndGet())
            .pincode(UUID.randomUUID().toString())
            .area(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .stateProvince(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString());
    }
}
